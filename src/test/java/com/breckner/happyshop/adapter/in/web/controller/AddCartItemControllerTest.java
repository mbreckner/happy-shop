package com.breckner.happyshop.adapter.in.web.controller;

import com.breckner.happyshop.adapter.in.web.JsonHelper;
import com.breckner.happyshop.application.port.in.AddCartItemsUseCase;
import com.breckner.happyshop.domain.model.Barcode;
import com.breckner.happyshop.domain.model.CartId;
import com.breckner.happyshop.domain.model.CartItemId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {
    AddCartItemController.class,
    AddCartItemsUseCase.class
})
class AddCartItemControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddCartItemsUseCase addCartItemsUseCase;

    @Test
    void shouldReturn204() throws Exception {
        mockMvc.perform(postRequest("request/add_cart_item.json", "123"))
            .andExpect(status().isNoContent());

        then(addCartItemsUseCase).should().addCartItems(refEq(new AddCartItemsUseCase.AddCartItemsInput(
            CartId.of("123"),
            Barcode.of("barcode"),
            CartItemId.of("cartItemId"),
            BigDecimal.TEN
        )));
    }

    private MockHttpServletRequestBuilder postRequest(String fileName, String id) {

        return post(String.format("/cart/%s/items", id))
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.readAsJsonString(fileName));
    }

}