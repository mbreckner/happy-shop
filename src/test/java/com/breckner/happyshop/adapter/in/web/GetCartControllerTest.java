package com.breckner.happyshop.adapter.in.web;

import com.breckner.happyshop.adapter.in.web.getcart.GetCartController;
import com.breckner.happyshop.adapter.in.web.getcart.GetCartResponseDtoMapper;
import com.breckner.happyshop.application.port.in.GetCartUseCase;
import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {
    GetCartController.class,
    GetCartUseCase.class,
    GetCartResponseDtoMapper.class
})
class GetCartControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetCartUseCase getCartUseCase;
    @MockBean
    private GetCartResponseDtoMapper getCartResponseDtoMapper;

    @Test
    void shouldReturn200() throws Exception {
        when(getCartUseCase.get(any())).thenReturn(mockShoppingCart());
        when(getCartResponseDtoMapper.toResponseDto(any())).thenReturn(mockResponseDto());
        mockMvc.perform(getRequest("1"))
            .andExpect(status().isOk())
            .andExpect(content().json(JsonHelper.readAsJsonString("response/get_cart_response.json")));
        ;

        then(getCartUseCase).should().get(CartId.of("1"));
    }

    private MockHttpServletRequestBuilder getRequest(String id) {
        return get(String.format("/cart/%s", id));
    }

    private ShoppingCart mockShoppingCart() {
        return ShoppingCart.create(Country.SWITZERLAND);
    }

    private GetCartController.GetCartResponseDto mockResponseDto() {
        return new GetCartController.GetCartResponseDto(
            "1",
            BigDecimal.valueOf(1.22),
            "CHE",
            "Switzerland",
            Instant.parse("2022-03-09T10:15:30Z").atZone(ZoneId.of("UTC")),
            "CHF",
            List.of()
        );
    }
}