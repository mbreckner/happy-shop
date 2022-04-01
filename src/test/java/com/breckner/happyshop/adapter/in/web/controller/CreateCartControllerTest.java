package com.breckner.happyshop.adapter.in.web.controller;

import com.breckner.happyshop.adapter.in.web.JsonHelper;
import com.breckner.happyshop.application.port.in.CreateCartUseCase;
import com.breckner.happyshop.domain.model.Country;
import com.breckner.happyshop.domain.model.ShoppingCart;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {
    CreateCartController.class,
    CreateCartUseCase.class
})
class CreateCartControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCartUseCase createCartUseCase;

    @Test
    void shouldReturn201() throws Exception {
        when(createCartUseCase.create(any())).thenReturn(ShoppingCart.create(Country.SWITZERLAND));
        mockMvc.perform(postRequest("request/create_cart.json"))
            .andExpect(status().isCreated());

        then(createCartUseCase).should().create(refEq(new CreateCartUseCase.CreateCartInput(
            Country.SWITZERLAND
        )));
    }

    @Test
    void shouldReturn400_WhenCountryIsInvalid() throws Exception {
        mockMvc.perform(postRequest("request/create_cart_invalid_country.json"))
            .andExpect(status().isBadRequest());
    }

    private MockHttpServletRequestBuilder postRequest(String fileName) {

        return post("/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonHelper.readAsJsonString(fileName));
    }

}