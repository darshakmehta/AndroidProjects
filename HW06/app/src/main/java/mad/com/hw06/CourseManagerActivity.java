package mad.com.hw06;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class CourseManagerActivity extends AppCompatActivity implements CourseFragment.CoursesFragmentOnFragmentInteractionListener, InstructorFragment.OnInstructorFragmentInteractionListener, InstructorListFragment.OnInstructorListFragmentInteractionListener, AddCourseFragment.OnAddCourseFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manager);
        getFragmentManager().beginTransaction().add(R.id.fragmentContainer, new CourseFragment(), "courseFragment").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_list_items,menu);
        //menu.getItem(0).setEnabled(false);
        //menu.getItem(3).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.home:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new CourseFragment() , "courseFragment").addToBackStack(null).commit();
                break;
            case R.id.instructors:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new InstructorListFragment() , "instructorListFragment").addToBackStack(null).commit();
                break;
            case R.id.add_instructor:
                getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new InstructorFragment() , "instructorFragment").addToBackStack(null).commit();
                break;
            case R.id.logout:
                Intent intent = new Intent(CourseManagerActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.exit:
                finishAffinity();
                break;
        }
        return true;
    }

    @Override
    public void onCourseFragmentInteraction(String message) {

    }

    @Override
    public void onInstructorFragmentInteraction(String msg) {

    }

    @Override
    public void onInstructorListFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAddCourseFragmentInteraction(Uri uri) {

    }
}
