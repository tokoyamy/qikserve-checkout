package com.qikserve.checkout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qikserve.checkout.Dto.CartDTO;
import com.qikserve.checkout.Dto.CartItemDTO;
import com.qikserve.checkout.controller.CheckoutController;
import com.qikserve.checkout.service.CheckoutService;
import com.qikserve.checkout.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CheckoutController.class)
class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private CheckoutService checkoutService;

    @InjectMocks
    private CheckoutController checkoutController;

    @Autowired
    private ObjectMapper objectMapper;

    private CartDTO cartDTO;

    @BeforeEach
    void setUp() {
        cartDTO = new CartDTO(List.of(new CartItemDTO("PWWe3w1SDU", 3)));
    }

    @Test
    void shouldProcessCheckoutSuccessfully() throws Exception {
        when(checkoutService.calculateTotal(cartDTO))
                .thenReturn(1998);

        mockMvc.perform(post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"total\":1998,\"savings\":999}"));
    }
}
