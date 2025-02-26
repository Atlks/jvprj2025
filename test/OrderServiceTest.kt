import apiAcc.RechargeHdr
import entityx.ReChgOrd
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.mockito.Mockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class OrderServiceTest {
    @InjectMocks
    private lateinit var RechargeHdr1: RechargeHdr

    @Mock
    private lateinit var mockOrder: ReChgOrd  // Mock ChgOrd 对象

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this) // 初始化 Mockito
    }

    @Test
    fun `should throw exception when ordDto is null`() {
        val exception = assertThrows<Exception> {
            RechargeHdr1.addChgOrd(null) // 传入 null 应该抛出异常
        }
        assertTrue(exception.message?.contains("ordDto is required") ?: false)
    }

    @Test
    fun `should add order successfully`() {
        `when`(mockOrder.id).thenReturn("12345")  // Mock 方法返回值

        assertDoesNotThrow {
            RechargeHdr1.addChgOrd(mockOrder) // 传入 Mock 对象
        }
    }
}
