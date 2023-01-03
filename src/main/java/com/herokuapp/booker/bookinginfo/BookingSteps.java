package com.herokuapp.booker.bookinginfo;

import com.herokuapp.booker.constants.EndPoints;
import com.herokuapp.booker.model.BookingPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;


public class BookingSteps {
    @Step("Getting Authentication with username : {0}, password: {1}")
    public ValidatableResponse getAuthentication() {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setUsername("admin");
        bookingPojo.setPassword("password123");
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bookingPojo)
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then();
    }

    @Step("Creating Booking with firstName : {0}, lastName: {1}, totalPrice {2} ,depositPaid {3} ,bookingDate {4}, additionalNeeds {5}")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, Boolean depositpaid, BookingPojo.BookingDates bookingdates, String additionalneeds) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setUsername("admin");
        bookingPojo.setPassword("password123");
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalneeds);

        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .body(bookingPojo)
                .when()
                .post()
                .then();

    }
    @Step("Get bookings with BookingId: {0}")
    public ValidatableResponse getBookingWithSingleBookingId(int id) {
        return SerenityRest.given().log().all()
               .header("Content-Type", "application/json")
               .header("Accept", "application/json")
                .pathParam("id",id)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then();
    }
    @Step("Update booking with bookingId: {0}, firstName: {1}, lastName: {2}, totalPrice: {3}, depositPaid: {4}, " +
            "checkIn: {5}, checkOut: {6} and additionalNeeds: {7}")
    public ValidatableResponse updateBooking(int id, String firstName, String lastName, int totalPrice,
                                             boolean depositPaid, BookingPojo.BookingDates bookingdates,
                                             String additionalNeeds,String token) {
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setUsername("admin");
        bookingPojo.setPassword("password123");
        bookingPojo.setFirstname(firstName);
        bookingPojo.setLastname(lastName);
        bookingPojo.setTotalprice(totalPrice);
        bookingPojo.setDepositpaid(depositPaid);
        bookingPojo.setBookingdates(bookingdates);
        bookingPojo.setAdditionalneeds(additionalNeeds);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + token)
                .pathParam("id", id)
                .body(bookingPojo)
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then().log().all().statusCode(200);
    }
    @Step("Delete bookings with BookingId: {0}")
    public ValidatableResponse deleteBookingWithBookingId(int id, String token) {
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then();
    }
    @Step("Getting booking info by ID")
    public ValidatableResponse getBookingInfoByID() {
        return SerenityRest.given()
                .when()
                .get()
                .then().statusCode(200);
    }
    }

