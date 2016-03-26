package com.mirhoseini.marketprice;

import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;

import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.marketprice.ui.main.view.MainActivity;
import com.mirhoseini.marketprice.utils.Constants;

import static android.support.test.espresso.Espresso.onView;


/**
 * Created by Mohsen on 3/26/16.
 */
//@RunWith(AndroidJUnit4.class)
//@LargeTest
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
        clearSharedPref();
        getActivity();
    }

    private void clearSharedPref() {
        AppSettings.clearValue(getActivity(), Constants.LAST_TIMESPAN);
    }

    public void testLayout() throws Exception {
        // arrange

        // act

        // assert
        onView(withId(R.id.progress))
                .check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.progress_message))
                .check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.enter_button))
                .check(ViewAssertions.matches(isDisplayed()));
    }
//
//    public void testLoginSuccess() throws Exception {
//        // arrange
//        doAnswer(new Answer() {
//            @Override
//            public Object answer(InvocationOnMock invocation) throws Throwable {
//                UserApiService.Callback callback = (UserApiService.Callback) invocation.getArguments()[2];
//                callback.onSuccess();
//                return null;
//            }
//        }).when(mMockUserService).login(anyString(), anyString(), any(UserApiService.Callback.class));
//
//        // act
//        onView(withId(R.id.email))
//                .perform(ViewActions.typeText("whateverEmail"));
//        onView(withId(R.id.password))
//                .perform(ViewActions.typeText("whateverPassword"));
//        onView(withId(R.id.enter_button))
//                .perform(ViewActions.click());
//
//        // assert
//        onView(withText("Second Page"))
//                .check(ViewAssertions.matches(isDisplayed()));
//    }
}