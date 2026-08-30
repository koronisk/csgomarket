import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import ru.let.csgomarket.Market
import ru.let.csgomarket.common.Currency
import kotlin.test.Test

internal class TodoRepositoryTest {
    lateinit var market: Market

    @BeforeEach
    fun setUp() {
        market = Market()
    }

    @AfterEach
    fun cleanUp() {
        market.close()
    }

    @Test
    @DisplayName("Price list should be not empty")
    fun shouldBeLoaded() = runBlocking {
        val prices = market.fetchPrices(Currency.RUB)
        Assertions.assertTrue(prices.count() > 1)
    }
}