package edu.skku.map.project_2017312665;

import org.junit.Test;

import static org.junit.Assert.*;

import android.app.Activity;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.skku.map.project_2017312665.ShoppingMall.ShoppingMallActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Activity activity = new ShoppingMallActivity();
        LinearLayout layout = activity.findViewById(R.layout.activity_shoppingmall);
        BottomNavigationView bot = layout.findViewById(R.id.nav_bar);

        assertEquals(100, bot.getHeight());


        ;
    }
}