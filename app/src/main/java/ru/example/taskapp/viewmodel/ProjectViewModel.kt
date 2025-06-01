package ru.example.taskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.example.taskapp.data.model.Project
import ru.example.taskapp.data.model.User
import ru.example.taskapp.data.repository.ProjectRepository
import ru.example.taskapp.data.repository.UserRepository

class ProjectViewModel(
    private val projectRepository: ProjectRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _projectName = MutableStateFlow("")
    val projectName: StateFlow<String> = _projectName.asStateFlow()

    private val _projectDescription = MutableStateFlow("")
    val projectDescription: StateFlow<String> = _projectDescription.asStateFlow()

    private val _projectOwner = MutableStateFlow<User?>(null)
    val projectOwner: StateFlow<User?> = _projectOwner.asStateFlow()

    private val _managers = MutableStateFlow<List<User>>(emptyList())
    val managers: StateFlow<List<User>> = _managers.asStateFlow()

    private val _project = MutableStateFlow<Project?>(null)
    val project: StateFlow<Project?> = _project.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    private val _createProjectResult = MutableStateFlow<Result<Project>?>(null)
    val createProjectResult: StateFlow<Result<Project>?> = _createProjectResult.asStateFlow()

    fun onProjectNameChanged(name: String) {
        _projectName.value = name
    }

    fun onProjectDescriptionChanged(description: String) {
        _projectDescription.value = description
    }

    fun onProjectOwnerSelected(user: User) {
        _projectOwner.value = user
    }

    sealed class ProjectState{
        object Loading: ProjectState()
        data class Success(val projects: List<Project>) : ProjectState()
        data class Error(val message: String) : ProjectState()
    }

    private val _projectsState = MutableStateFlow<ProjectState>(ProjectState.Loading)
    val projectsState: StateFlow<ProjectState> = _projectsState

    private val _selectedProject = MutableStateFlow<Project?>(null)
    val selectedProject: StateFlow<Project?> = _selectedProject

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole.asStateFlow()

    init {
        loadRole()
        loadProjects()
    }

    private fun loadRole() {
        viewModelScope.launch {
            val role = projectRepository.getUserRole()
            _userRole.value = role
        }
    }

    fun loadProjects() {
        viewModelScope.launch {
            _projectsState.value = ProjectState.Loading
            try {
                val result = projectRepository.getProjects()
                result.onSuccess { projects ->
                    _projectsState.value = ProjectState.Success(projects)
                }.onFailure { e ->
                    _projectsState.value = ProjectState.Error(e.message ?: "An unexpected error occurred")
                }
            } catch (e: Exception) {
                _projectsState.value = ProjectState.Error(e.message ?: "An unexpected error occurred")
            }

        }
    }

    fun selectProject(project: Project) {
        _selectedProject.value = project
    }

    fun clearSelectedProject() {
        _selectedProject.value = null
    }

    fun loadManagers() {
        viewModelScope.launch {
            try {
                val allUsers = userRepository.getUsers().getOrThrow()
                val managers = allUsers.filter { it.type == "manager" }
                _managers.value = managers
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun loadProject(projectId: Int) {
        viewModelScope.launch {
            _projectsState.value = ProjectState.Loading
            try {
                val result = projectRepository.getProjectById(projectId)
                result.onSuccess { project ->
                    _project.value = project
                    _projectName.value = project.title
                    _projectDescription.value = project.description ?: ""
                    _projectOwner.value = _managers.value.find { it.id == project.ownerId }
                }.onFailure { e ->
                    _projectsState.value = ProjectState.Error(e.message ?: "An unexpected error occurred")
                }
            } catch (e: Exception) {
                _projectsState.value = ProjectState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }
}