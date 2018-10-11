package edu.awilkins6gatech.happyhealthytummyapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

public class BaseTestClass {
    protected final Context appContext = InstrumentationRegistry.getTargetContext();
}
