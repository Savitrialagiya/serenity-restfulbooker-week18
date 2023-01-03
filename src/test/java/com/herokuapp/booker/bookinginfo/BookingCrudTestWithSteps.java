package com.herokuapp.booker.bookinginfo;

import com.herokuapp.booker.model.BookingPojo;
import com.herokuapp.booker.testbase.TestBase;
import com.herokuapp.booker.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class BookingCrudTestWithSteps extends TestBase {
    static String username = "admin";
    static String password = "password123";
    static String firstname = "xyz" + TestUtils.getRandomValue();
    static String lastname = "coldo" + TestUtils.getRandomValue();
    static int totalprice = 50;
    static Boolean depositpaid = true;
    static String additionalneeds = "Breakfast";
    static int id;
    static String token;

    @Steps
    BookingSteps bookingSteps;

    @Title("This is for authentication")
    @Test
    public void test001() {
       ValidatableResponse response = bookingSteps.getAuthentication().statusCode(200);
       token = response.extract().path("token");
    }

    @Title("This will create new booking")
    @Test
    public void test002() {
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2022-10-01");
        bookingdates.setCheckout("2022-12-01");

        ValidatableResponse response = bookingSteps.createBooking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
        response.log().all().statusCode(200);
        id = response.extract().path("bookingid");

    }

    @Title("This method will verify new Booking ID creation ")
    @Test
    public void test003() {
        ValidatableResponse response = bookingSteps.getBookingInfoByID();
        ArrayList<?> booking = response.extract().path("bookingid");
        Assert.assertTrue(booking.contains(id));
    }
    @Title("This method will get booking with Id")
    @org.junit.Test
    public void test004() {
        bookingSteps.getBookingWithSingleBookingId(id).statusCode(200);
    }

    @Title("This method will updated a booking with ID")
    @Test
    public void test005() {

        firstname = "Parv";
        BookingPojo.BookingDates bookingdates = new BookingPojo.BookingDates();
        bookingdates.setCheckin("2022-10-01");
        bookingdates.setCheckout("2022-12-01");
        bookingSteps.updateBooking(id, firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds, token);
        ValidatableResponse response = bookingSteps.getBookingWithSingleBookingId(id);
        HashMap<String, ?> update = response.extract().path("");
        Assert.assertThat(update, hasValue("Parv"));


    }

    @Title("Delete the booking and verify")
    @Test
    public void test006() {
        bookingSteps.deleteBookingWithBookingId(id, token).log().all().statusCode(201);
        bookingSteps.getBookingWithSingleBookingId(id).log().all().statusCode(404);

    }

}
