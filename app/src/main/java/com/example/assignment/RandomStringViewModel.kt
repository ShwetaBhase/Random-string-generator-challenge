import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.assignment.AppDatabase
import com.example.assignment.RandomString
import com.example.assignment.RandomStringRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RandomStringViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RandomStringRepository
    val allStrings: StateFlow<List<RandomString>>

    init {
        val dao = AppDatabase.getDatabase(application).randomStringDao()
        repository = RandomStringRepository(dao)


        allStrings = repository.allStrings
            .map { it }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun insert(randomString: RandomString) = viewModelScope.launch {
        repository.insert(randomString)
    }

    fun delete(randomString: RandomString) = viewModelScope.launch {
        repository.delete(randomString)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}
