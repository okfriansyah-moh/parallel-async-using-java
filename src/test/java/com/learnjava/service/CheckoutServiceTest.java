package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void no_of_cores() {
        //given

        //when
        System.out.println("no fo cores : " + Runtime.getRuntime().availableProcessors());

        //then
    }


    @Test
    void checkOut_6_items() {
        // given
        Cart cart = DataSet.createCart(6);

        // when
        CheckoutResponse checkoutResponse = checkoutService.checkOut(cart);

        // then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkOut_13_items() {
        // given
        Cart cart = DataSet.createCart(13);

        // when
        CheckoutResponse checkoutResponse = checkoutService.checkOut(cart);

        // then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }
}
