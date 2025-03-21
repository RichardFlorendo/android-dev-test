import android.widget.FrameLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokedex_app.viewmodel.recyclerview.PokemonAdapter
import com.example.pokedex_app.viewmodel.recyclerview.PokemonRecyclerItem
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonAdapterTest {

    private lateinit var adapter: PokemonAdapter

    private val mockList = listOf(
        PokemonRecyclerItem("Eevee", "url"),
        PokemonRecyclerItem("Jigglypuff", "url")
    )

    @Before
    fun setup() {
        adapter = PokemonAdapter(mockList) {}
    }

    @Test
    fun returns_Correct_Itemcount() {
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun onCreateViewHolder_creates_correct_view() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val parent = FrameLayout(context) // FrameLayout is a valid ViewGroup

        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        // Check if viewHolder is created correctly
        assert(viewHolder.itemView != null)
    }
}