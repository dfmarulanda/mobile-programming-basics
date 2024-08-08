# Sesión 2: Modelo Vista Controlador - MVC (Ampliado)

## 1. Profundización en el Modelo Vista Controlador (MVC)

### 1.1 Orígenes y Evolución del MVC

El patrón MVC fue descrito por primera vez en 1979 por Trygve Reenskaug, quien trabajaba en Smalltalk en los laboratorios de investigación de Xerox. Desde entonces, ha evolucionado y se ha adaptado a diferentes plataformas y paradigmas de programación.

La idea fundamental detrás de MVC es la separación de preocupaciones (separation of concerns), un principio de diseño de software que sugiere dividir un programa en secciones distintas, cada una abordando un conjunto separado de preocupaciones.

### 1.2 Componentes del MVC en Detalle

#### 1.2.1 Modelo

El Modelo representa los datos y la lógica de negocio de la aplicación. Es independiente de la interfaz de usuario y del controlador.

Responsabilidades del Modelo:
- Encapsular los datos de la aplicación
- Implementar la lógica de negocio
- Definir reglas para la manipulación de datos
- Notificar a los observadores (generalmente la Vista) cuando hay cambios en los datos

Ejemplo expandido de un Modelo en Kotlin:

```kotlin
class TaskModel {
    private val tasks = mutableListOf<Task>()
    private val observers = mutableListOf<TaskObserver>()
    private var lastId = 0

    fun addTask(title: String, category: String) {
        val task = Task(++lastId, title, category)
        tasks.add(task)
        notifyObservers()
    }

    fun removeTask(id: Int) {
        tasks.removeAll { it.id == id }
        notifyObservers()
    }

    fun updateTask(id: Int, title: String, category: String) {
        tasks.find { it.id == id }?.let {
            it.title = title
            it.category = category
            notifyObservers()
        }
    }

    fun toggleTaskCompletion(id: Int) {
        tasks.find { it.id == id }?.let {
            it.isCompleted = !it.isCompleted
            notifyObservers()
        }
    }

    fun getAllTasks(): List<Task> = tasks.toList()

    fun getTasksByCategory(category: String): List<Task> = 
        tasks.filter { it.category == category }

    fun getAllCategories(): Set<String> = 
        tasks.map { it.category }.toSet()

    fun addObserver(observer: TaskObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: TaskObserver) {
        observers.remove(observer)
    }

    private fun notifyObservers() {
        observers.forEach { it.onTasksChanged() }
    }
}

interface TaskObserver {
    fun onTasksChanged()
}
```

Este Modelo expandido incluye un sistema de observadores para notificar a las Vistas cuando los datos cambian, lo cual es una característica común en implementaciones más robustas de MVC.

#### 1.2.2 Vista

La Vista es responsable de la presentación de los datos al usuario. En Android, esto generalmente implica layouts XML y componentes de UI personalizados.

Responsabilidades de la Vista:
- Renderizar la interfaz de usuario
- Recibir entradas del usuario
- Mantener consistencia en la presentación cuando el Modelo cambia

Ejemplo de una Vista más compleja en XML:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp">

    <EditText
        android:id="@+id/taskInput"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:hint="Enter a new task"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toStartOf="@id/addButton"/>

    <Spinner
        android:id="@+id/categorySpinner"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintTop_toBottomOf="@id/taskInput"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toStartOf="@id/addButton"/>

    <Button
        android:id="@+id/addButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Add Task"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/taskList"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@id/categorySpinner"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"/>

</androidx.constraintlayout.widget.ConstraintLayout>
```

#### 1.2.3 Controlador

El Controlador actúa como un intermediario entre el Modelo y la Vista. Recibe las entradas del usuario desde la Vista, las procesa (con la ayuda del Modelo si es necesario), y devuelve el resultado a la Vista.

Responsabilidades del Controlador:
- Recibir entradas del usuario desde la Vista
- Procesar las entradas del usuario, con la ayuda del Modelo si es necesario
- Determinar qué Vista mostrar al usuario

Ejemplo expandido de un Controlador en Kotlin:

```kotlin
class TaskController(private val model: TaskModel) : TaskObserver {
    private var view: TaskView? = null

    fun attachView(view: TaskView) {
        this.view = view
        model.addObserver(this)
        updateView()
    }

    fun detachView() {
        model.removeObserver(this)
        this.view = null
    }

    fun addTask(title: String, category: String) {
        if (title.isNotEmpty() && category.isNotEmpty()) {
            model.addTask(title, category)
        } else {
            view?.showError("Title and category cannot be empty")
        }
    }

    fun removeTask(id: Int) {
        model.removeTask(id)
    }

    fun toggleTaskCompletion(id: Int) {
        model.toggleTaskCompletion(id)
    }

    fun loadTasks(category: String?) {
        val tasks = if (category == null || category == "All") {
            model.getAllTasks()
        } else {
            model.getTasksByCategory(category)
        }
        view?.displayTasks(tasks)
    }

    fun loadCategories() {
        val categories = model.getAllCategories().toMutableList()
        categories.add(0, "All")
        view?.displayCategories(categories)
    }

    override fun onTasksChanged() {
        updateView()
    }

    private fun updateView() {
        loadTasks(null)
        loadCategories()
    }
}

interface TaskView {
    fun displayTasks(tasks: List<Task>)
    fun displayCategories(categories: List<String>)
    fun showError(message: String)
}
```

En esta implementación, el Controlador mantiene una referencia al Modelo y a la Vista (a través de una interfaz). Esto permite una mejor separación de responsabilidades y facilita las pruebas unitarias.

### 1.3 Flujo de Control en MVC

1. El usuario interactúa con la Vista (por ejemplo, haciendo clic en un botón).
2. La Vista notifica al Controlador sobre la acción del usuario.
3. El Controlador accede al Modelo, posiblemente actualizándolo según la acción del usuario.
4. El Modelo notifica a sus observadores (generalmente la Vista) que los datos han cambiado.
5. La Vista consulta al Modelo para obtener los datos actualizados y se actualiza en consecuencia.
6. La interfaz de usuario espera más interacciones del usuario, lo que comienza el ciclo nuevamente.

### 1.4 Ventajas y Desventajas del MVC en Android

Ventajas:
1. Separación clara de responsabilidades
2. Facilita el desarrollo paralelo (diferentes desarrolladores pueden trabajar en el Modelo, la Vista y el Controlador simultáneamente)
3. Alta cohesión (funcionalidades fuertemente relacionadas están juntas)
4. Bajo acoplamiento (cambios en una parte tienen un impacto mínimo en otras partes)
5. Facilita las pruebas unitarias, especialmente del Modelo y el Controlador

Desventajas:
1. Puede llevar a un Controlador "gordo" que maneja demasiada lógica
2. En Android, la línea entre Vista y Controlador puede volverse borrosa, ya que las Activities y Fragments a menudo asumen ambos roles
3. Para aplicaciones pequeñas, MVC puede parecer excesivo
4. La navegación de la aplicación puede volverse complicada de manejar en el Controlador

### 1.5 Consideraciones prácticas al implementar MVC en Android

1. **Manejo del ciclo de vida**: Las Activities y Fragments en Android tienen un ciclo de vida complejo. Es importante considerar cómo esto afecta a tu implementación de MVC, especialmente en términos de retener el estado y manejar las referencias a la Vista.

2. **Inyección de dependencias**: Considera usar un framework de inyección de dependencias como Dagger o Hilt para manejar la creación y el ciclo de vida de tus componentes MVC.

3. **Programación reactiva**: Librerías como RxJava o Kotlin Coroutines pueden ser útiles para manejar las actualizaciones del Modelo y la comunicación entre componentes de manera asíncrona.

4. **Testing**: MVC facilita las pruebas unitarias. Asegúrate de escribir pruebas para tu Modelo y Controlador. Para la Vista, considera usar pruebas de UI con Espresso.

5. **Persistencia de datos**: Considera usar Room o alguna otra solución de base de datos para persistir tu Modelo. Esto puede ayudar a manejar los cambios de configuración y el ciclo de vida de la aplicación.

## 2. Ejercicio Práctico Avanzado

Vamos a expandir nuestra aplicación de lista de tareas para incluir más funcionalidades y demostrar un uso más avanzado de MVC.

### Objetivo
Crear una aplicación de gestión de tareas con las siguientes características:
- Agregar, editar y eliminar tareas
- Categorizar tareas
- Filtrar tareas por categoría y estado (completado/pendiente)
- Ordenar tareas por fecha de creación o prioridad
- Persistir datos usando Room

### Pasos

1. Actualiza el Modelo para incluir más detalles en las tareas:

```kotlin
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "priority") var priority: Int,
    @ColumnInfo(name = "due_date") var dueDate: Long,
    @ColumnInfo(name = "is_completed") var isCompleted: Boolean = false,
    @ColumnInfo(name = "created_at") var createdAt: Long = System.currentTimeMillis()
)
```

2. Implementa el DAO (Data Access Object) para Room:

```kotlin
@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    @Query("SELECT * FROM tasks WHERE category = :category")
    fun getTasksByCategory(category: String): List<Task>

    @Insert
    fun insertTask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)
}
```

3. Actualiza el Modelo para usar Room:

```kotlin
class TaskModel(private val taskDao: TaskDao) {
    private val observers = mutableListOf<TaskObserver>()

    fun addTask(task: Task) {
        taskDao.insertTask(task)
        notifyObservers()
    }

    fun updateTask(task: Task) {
        taskDao.updateTask(task)
        notifyObservers()
    }

    fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
        notifyObservers()
    }

    fun getAllTasks(): List<Task> = taskDao.getAllTasks()

    fun getTasksByCategory(category: String): List<Task> = 
        taskDao.getTasksByCategory(category)

    fun getAllCategories(): Set<String> = 
        getAllTasks().map { it.category }.toSet()

    // ... métodos de observador ...
}
```

4. Expande el Controlador para manejar las nuevas funcionalidades:

```kotlin
class TaskController(private val model: TaskModel) : TaskObserver {
    private var view: TaskView? = null

    // ... métodos existentes ...

    fun addTask(title: String, description: String, category: String, priority: Int, dueDate: Long) {
        if (title.isNotEmpty() && category.isNotEmpty()) {
            val newTask = Task(0, title, description, category, priority, dueDate)
            model.addTask(newTask)
        } else {
            view?.showError("Title and category cannot be empty")
        }
    }

    fun updateTask(task: Task) {
        model.updateTask(task)
    }

    fun deleteTask(task: Task) {
        model.deleteTask(task)
    }

    fun loadTasks(category: String?, filterCompleted: Boolean?, sortBy: SortCriteria) {
        var tasks = if (category == null || category == "All") {
            model.getAllTasks()
        } else {
            model.getTasksByCategory(category)
        }

        filterCompleted?.let { completed ->
            tasks = tasks.filter { it.isCompleted == completed }
        }

        tasks = when (sortBy) {
            SortCriteria.DATE -> tasks.sortedBy { it.createdAt }
            SortCriteria.PRIORITY -> tasks.sortedByDescending { it.priority }
        }

        view?.displayTasks(tasks)
    }

    // ... otros métodos ...
}

enum class SortCriteria {
    DATE, PRIORITY
}
```

5. Actualiza la Vista para incluir las nuevas funcionalidades:

```kotlin
interface Task5. Actualiza la Vista para incluir las nuevas funcionalidades:

```kotlin
interface TaskView {
    fun displayTasks(tasks: List<Task>)
    fun displayCategories(categories: List<String>)
    fun showError(message: String)
    fun showTaskDetails(task: Task)
    fun showAddTaskDialog()
    fun showEditTaskDialog(task: Task)
    fun showDeleteTaskConfirmation(task: Task)
}

class MainActivity : AppCompatActivity(), TaskView {
    private lateinit var controller: TaskController
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var categorySpinner: Spinner
    private lateinit var sortSpinner: Spinner
    private lateinit var filterSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar componentes de UI
        val recyclerView: RecyclerView = findViewById(R.id.taskRecyclerView)
        categorySpinner = findViewById(R.id.categorySpinner)
        sortSpinner = findViewById(R.id.sortSpinner)
        filterSwitch = findViewById(R.id.filterSwitch)
        val addButton: FloatingActionButton = findViewById(R.id.addTaskButton)

        // Configurar RecyclerView
        taskAdapter = TaskAdapter(emptyList(), ::onTaskClick, ::onTaskLongClick)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializar controlador
        val taskDao = TaskDatabase.getInstance(this).taskDao()
        val model = TaskModel(taskDao)
        controller = TaskController(model)
        controller.attachView(this)

        // Configurar listeners
        addButton.setOnClickListener { showAddTaskDialog() }
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                updateTaskList()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                updateTaskList()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        filterSwitch.setOnCheckedChangeListener { _, _ -> updateTaskList() }
    }

    private fun updateTaskList() {
        val category = if (categorySpinner.selectedItemPosition == 0) null 
                       else categorySpinner.selectedItem.toString()
        val sortCriteria = if (sortSpinner.selectedItemPosition == 0) 
                           SortCriteria.DATE else SortCriteria.PRIORITY
        val filterCompleted = if (filterSwitch.isChecked) false else null
        controller.loadTasks(category, filterCompleted, sortCriteria)
    }

    override fun displayTasks(tasks: List<Task>) {
        taskAdapter.updateTasks(tasks)
    }

    override fun displayCategories(categories: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showTaskDetails(task: Task) {
        // Implementar diálogo o actividad para mostrar detalles de la tarea
    }

    override fun showAddTaskDialog() {
        // Implementar diálogo para agregar nueva tarea
    }

    override fun showEditTaskDialog(task: Task) {
        // Implementar diálogo para editar tarea existente
    }

    override fun showDeleteTaskConfirmation(task: Task) {
        AlertDialog.Builder(this)
            .setTitle("Delete Task")
            .setMessage("Are you sure you want to delete this task?")
            .setPositiveButton("Yes") { _, _ -> controller.deleteTask(task) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun onTaskClick(task: Task) {
        showTaskDetails(task)
    }

    private fun onTaskLongClick(task: Task): Boolean {
        showDeleteTaskConfirmation(task)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.detachView()
    }
}
```

### 3. Consideraciones Avanzadas en la Implementación de MVC

#### 3.1 Manejo de Estado y Configuración de Cambios

En Android, las actividades pueden ser destruidas y recreadas debido a cambios de configuración (como rotaciones de pantalla). Esto presenta un desafío para mantener el estado de la aplicación. Algunas estrategias para manejar esto en MVC incluyen:

1. Usar `ViewModel` de la arquitectura de componentes de Android para mantener el estado durante los cambios de configuración.
2. Implementar `onSaveInstanceState` y `onRestoreInstanceState` para guardar y restaurar el estado de la UI.
3. Utilizar un Modelo persistente (como con Room en nuestro ejemplo) para mantener los datos independientemente del ciclo de vida de la Activity.

#### 3.2 Comunicación Asíncrona

En aplicaciones del mundo real, muchas operaciones (como la carga de datos desde una base de datos o red) son asíncronas. Podemos manejar esto en MVC de varias maneras:

1. Usando Callbacks:

```kotlin
interface TaskCallback {
    fun onTasksLoaded(tasks: List<Task>)
    fun onError(error: String)
}

class TaskModel(private val taskDao: TaskDao) {
    fun getAllTasksAsync(callback: TaskCallback) {
        Thread {
            try {
                val tasks = taskDao.getAllTasks()
                Handler(Looper.getMainLooper()).post {
                    callback.onTasksLoaded(tasks)
                }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post {
                    callback.onError(e.message ?: "Unknown error")
                }
            }
        }.start()
    }
}
```

2. Usando Coroutines (recomendado para Kotlin):

```kotlin
class TaskModel(private val taskDao: TaskDao) {
    suspend fun getAllTasks(): List<Task> = withContext(Dispatchers.IO) {
        taskDao.getAllTasks()
    }
}

class TaskController(private val model: TaskModel) {
    fun loadTasks() {
        viewModelScope.launch {
            try {
                val tasks = model.getAllTasks()
                view?.displayTasks(tasks)
            } catch (e: Exception) {
                view?.showError(e.message ?: "Unknown error")
            }
        }
    }
}
```

#### 3.3 Inyección de Dependencias

La inyección de dependencias puede ayudar a reducir el acoplamiento y mejorar la testabilidad de tu aplicación MVC. Puedes usar una biblioteca como Dagger o Hilt para esto:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskModel(taskDao: TaskDao): TaskModel {
        return TaskModel(taskDao)
    }

    @Provides
    fun provideTaskController(model: TaskModel): TaskController {
        return TaskController(model)
    }
}

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TaskView {
    @Inject lateinit var controller: TaskController

    // ...
}
```

#### 3.4 Testing en MVC

MVC facilita las pruebas unitarias al separar las responsabilidades. Aquí hay algunos ejemplos de cómo podrías probar cada componente:

1. Pruebas del Modelo:

```kotlin
@RunWith(AndroidJUnit4::class)
class TaskModelTest {
    private lateinit var taskDao: TaskDao
    private lateinit var taskModel: TaskModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java).build()
        taskDao = db.taskDao()
        taskModel = TaskModel(taskDao)
    }

    @Test
    fun addTask_insertsTaskIntoDb() = runBlocking {
        val task = Task(0, "Test Task", "Description", "Work", 1, System.currentTimeMillis())
        taskModel.addTask(task)
        val tasks = taskModel.getAllTasks()
        assertThat(tasks).contains(task)
    }
}
```

2. Pruebas del Controlador:

```kotlin
class TaskControllerTest {
    @Mock
    private lateinit var model: TaskModel
    @Mock
    private lateinit var view: TaskView
    private lateinit var controller: TaskController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = TaskController(model)
        controller.attachView(view)
    }

    @Test
    fun loadTasks_callsModelAndUpdatesView() = runBlocking {
        val tasks = listOf(Task(1, "Task 1", "Description", "Work", 1, System.currentTimeMillis()))
        whenever(model.getAllTasks()).thenReturn(tasks)

        controller.loadTasks(null, null, SortCriteria.DATE)

        verify(model).getAllTasks()
        verify(view).displayTasks(tasks)
    }
}
```

3. Pruebas de la Vista (pruebas de UI):

```kotlin
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun displayTasks_showsTasksInRecyclerView() {
        val tasks = listOf(Task(1, "Task 1", "Description", "Work", 1, System.currentTimeMillis()))
        
        activityRule.scenario.onActivity { activity ->
            activity.runOnUiThread {
                activity.displayTasks(tasks)
            }
        }

        onView(withId(R.id.taskRecyclerView))
            .check(matches(hasDescendant(withText("Task 1"))))
    }
}
```

### 4. Conclusión

El patrón MVC proporciona una estructura clara para organizar el código en aplicaciones Android, separando las responsabilidades y facilitando el mantenimiento y las pruebas. Sin embargo, a medida que las aplicaciones crecen en complejidad, pueden surgir limitaciones, como Controladores demasiado grandes o dificultades para manejar el estado de la aplicación.

Es importante recordar que MVC es solo uno de varios patrones de arquitectura disponibles. Dependiendo de las necesidades específicas de tu aplicación, podrías considerar alternativas como MVP (Modelo-Vista-Presentador), MVVM (Modelo-Vista-ViewModel), o arquitecturas más modernas como Clean Architecture o MVI (Modelo-Vista-Intención).

Lo más importante es elegir una arquitectura que promueva la separación de responsabilidades, facilite las pruebas y se adapte bien a las necesidades y escala de tu aplicación.

## 5. Recursos Adicionales

- [Guía de Arquitectura de Apps de Android](https://developer.android.com/jetpack/guide)
- [Codelab: Android Room con Vista](https://developer.android.com/codelabs/android-room-with-a-view-kotlin)
- [Pruebas en Android](https://developer.android.com/training/testing)
- [Inyección de dependencias con Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)