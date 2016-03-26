package com.mirhoseini.marketprice;


import com.mirhoseini.marketprice.ui.main.presenter.MainPresenter;
import com.mirhoseini.marketprice.ui.main.presenter.MainPresenterImpl;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Iterator;

import static org.mockito.Mockito.*;


/**
 * Created by Mohsen on 3/26/16.
 */
public class PresenterTest {

    @Mock
    MainPresenterImpl mainPresenter;

    @Test
    public void test1() {
        //  create mock
//        MainPresenterImpl mainPresenter = Mockito.mock(MainPresenterImpl.class);

        // define return value for method getUniqueId()
        when(mainPresenter.onBackPressed()).thenReturn(false);
        when(mainPresenter.onBackPressed()).thenReturn(true);

        // use mock in test....
        assertEquals(test.getUniqueId(), 43);
    }


    // Demonstrates the return of multiple values
    @Test
    public void testMoreThanOneReturnValue() {
        Iterator i = mock(Iterator.class);
        when(i.next()).thenReturn("Mockito").thenReturn("rocks");
        String result = i.next() + " " + i.next();
        //assert
        assertEquals("Mockito rocks", result);
    }

    // this test demonstrates how to return values based on the input
    @Test
    public void testReturnValueDependentOnMethodParameter() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo("Mockito")).thenReturn(1);
        when(c.compareTo("Eclipse")).thenReturn(2);
        //assert
        assertEquals(1, c.compareTo("Mockito"));
    }

// this test demonstrates how to return values independent of the input value

    @Test
    public void testReturnValueInDependentOnMethodParameter() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo(anyInt())).thenReturn(-1);
        //assert
        assertEquals(-1, c.compareTo(9));
    }

// return a value based on the type of the provide parameter

    @Test
    public void testReturnValueInDependentOnMethodParameter() {
        Comparable c = mock(Comparable.class);
        when(c.compareTo(isA(Todo.class))).thenReturn(0);
        //assert
        Todo todo = new Todo(5);
        assertEquals(todo, c.compareTo(new Todo(1)));
    }
}
