package mad.com.hw06;

/*
* Assignment HW06
* Gaurav Pareek
* Darshak Mehta
* MainActivity.java
* */
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity implements  LoginFragment.OnLoginFragmentInteractionListener, RegisterFragment.OnRegisterFragmentInteractionListener, CourseFragment.CoursesFragmentOnFragmentInteractionListener, InstructorFragment.OnInstructorFragmentInteractionListener, InstructorListFragment.OnInstructorListFragmentInteractionListener, AddCourseFragment.OnAddCourseFragmentInteractionListener, DisplayCourseFragment.OnDisplayCourseFragmentInteractionListener, DisplayInstructorFragment.OnDisplayInstructorFragmentInteractionListener{

    public static String username_loggedin_user = "-1";
    public static Menu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        getFragmentManager().beginTransaction().add(R.id.loginContainer, new LoginFragment(), "loginFragment").addToBackStack(null).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_list_items,menu);
        menu.getItem(3).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.home:
                if(MainActivity.username_loggedin_user.equals("-1")){
                    Toast.makeText(this, "Login/SignUp to proceed.", Toast.LENGTH_SHORT).show();
                }else{
                    getFragmentManager().beginTransaction().replace(R.id.loginContainer, new CourseFragment() , "courseFragment").addToBackStack(null).commit();
                }
                break;
            case R.id.instructors:
                if(MainActivity.username_loggedin_user.equals("-1")){
                    Toast.makeText(this, "Login/SignUp to proceed.", Toast.LENGTH_SHORT).show();
                }else{
                    getFragmentManager().beginTransaction().replace(R.id.loginContainer, new InstructorListFragment() , "instructorListFragment").addToBackStack(null).commit();
                }
                break;
            case R.id.add_instructor:
                if(MainActivity.username_loggedin_user.equals("-1")){
                    Toast.makeText(this, "Login/SignUp to proceed.", Toast.LENGTH_SHORT).show();
                }else{
                    getFragmentManager().beginTransaction().replace(R.id.loginContainer, new InstructorFragment() , "instructorFragment").addToBackStack(null).commit();
                }
                break;
            case R.id.logout:
                FragmentManager fm = getFragmentManager(); // or 'getSupportFragmentManager();'
                int count = fm.getBackStackEntryCount();
                for(int i = 0; i < count; ++i) {
                    fm.popBackStack();
                }
                MainActivity.username_loggedin_user = "-1";
                menu.getItem(3).setVisible(false);
                getFragmentManager().beginTransaction().replace(R.id.loginContainer, new LoginFragment(), "loginFragment").addToBackStack(null).commit();
                break;
            case R.id.exit:
                finishAffinity();
                break;
        }
        return true;
    }


    @Override
    public void onLoginFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRegisterFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCourseFragmentInteraction(String message) {

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            if(getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            }else{
                finishAffinity();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onInstructorListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onInstructorFragmentInteraction(String msg) {

    }

    @Override
    public void onAddCourseFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDisplayCourseFragmentInteraction(Uri uri) {

    }

    @Override
    public void onDisplayInstructorFragmentInteraction(Uri uri) {

    }
}
