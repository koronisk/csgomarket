import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import ru.let.csgomarket.Market
import ru.let.csgomarket.common.Currency
import kotlin.test.Test

internal class TodoRepositoryTest {
    companion object{
        lateinit var market: Market

        @JvmStatic
        @BeforeAll
        fun setUp() {
            market = Market()
        }

        @JvmStatic
        @AfterAll
        fun cleanUp() {
            market.close()
        }
    }

    @Test
    @DisplayName("Price list should be not empty")
    fun shouldBeLoaded() = runBlocking {
        val prices = market.fetchPrices(Currency.RUB)
        Assertions.assertTrue(prices.count() > 1)
    }
}