package com.example.mlab.examplerealm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.mlab.examplerealm.model.Task;
import com.example.mlab.examplerealm.model.User;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if (realm.where(User.class).count() == 0) {
                    User u = realm.createObject(User.class);
                    u.setId(UUID.randomUUID().toString());
                    u.setFirstName("Motebang");
                    u.setSecondName("Mokwatsi");
                }


                //used when realm object class extends RealmObject and not using @RealmClass Annotation and implements RealmModel
//                if(task.isValid())
//                {
//
//                }

//                if(RealmObject.isValid(task))
//                {
//
//                }
            }
        });

        User u = realm.where(User.class).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User u = realm.where(User.class).findFirst();
                Task task = realm.createObject(Task.class);
                task.setId(UUID.randomUUID().toString());
                task.setTitle("Take out Recycling");
                task.setDescription("Do it before Morning");
                u.setTask(task);
            }
        });


        //Old version using realm as a dependency

//        RealmResults<Task> realmResultsTask = realm.allObjects(Task.class)
//                .where()
//                .contains("title","GoodBye").findAll();

        //output the current task
        Log.d("Task", "Task Title" + u.getTask().getTitle());

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User u = realm.createObject(User.class);
                u.setFirstName("Bill");
                u.setSecondName("Smith");
                u.setId(UUID.randomUUID().toString());

                Task t = realm.createObject(Task.class);
                t.setId(UUID.randomUUID().toString());
                t.setTitle("Go Cycling");
                t.setDescription("Have fun too!");
                u.setTask(t);

            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User u = realm.createObject(User.class);
                u.setFirstName("Boksar");
                u.setSecondName("Lints");
                u.setId(UUID.randomUUID().toString());

                Task t = realm.createObject(Task.class);
                t.setId(UUID.randomUUID().toString());
                t.setTitle("Buy New Shoes");
                t.setDescription("Brown one, to match with the belt.");
                t.setCompleted(true);
                u.setTask(t);


            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User u = realm.createObject(User.class);
                u.setId(UUID.randomUUID().toString());
                u.setSecondName("Lopez");
                u.setFirstName("Suzi");


                Task t = realm.createObject(Task.class);
                t.setId(UUID.randomUUID().toString());
                t.setDescription("Take your time, it is important");
                t.setTitle("Review pull request for release.");
                u.setTask(t);

            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User u = realm.createObject(User.class);
                u.setFirstName("Warren");
                u.setSecondName("Chu");
                u.setId(UUID.randomUUID().toString());

                Task t = realm.createObject(Task.class);
                t.setId(UUID.randomUUID().toString());
                t.setTitle("Finish Budget");
                t.setDescription("Finalize runway calculation too.");
                u.setTask(t);

            }
        });

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                User u = realm.createObject(User.class);
                u.setFirstName("Wendy");
                u.setSecondName("Jackson");
                u.setId(UUID.randomUUID().toString());


                Task t = realm.createObject(Task.class);
                u.setId(UUID.randomUUID().toString());
                t.setTitle("Finish Marketing Plan");
                t.setDescription("Include social account metrics in calculations");
                u.setTask(t);
            }
        });

        RealmResults<Task> tasks = realm.where(Task.class)
                .beginsWith("title", "Finish")
                .findAll();

        Log.d(TAG, "Task that have a title starting with 'finish': " + Integer.toString(tasks.size()));

        //find all users who  have a task title that starts with finish


        RealmResults<User> users =realm.where(User.class)
                .beginsWith("task.title", "finish",Case.INSENSITIVE)
                .findAll();

        //looping through the  realm results and getting names of users that task title start with finish and task title
        for(int i= 0 ;i<users.size();i++)

        {
            User temp = users.get(i);
            Log.d(TAG," Users names with task title starting with Finish: "+temp.getFirstName()+
                    "\nUsers found that have a task title starting with 'finish':"+temp.getTask().getTitle());
        }

        //showing the number of users task starting with Finish
        Log.d(TAG,"Users found that have a task title starting with 'finish': "+Integer.toString(users.size()));

        //check which users tasks are completed

        RealmResults<User> usr = realm.where(User.class)
                .equalTo("task.isCompleted",true)
                .findAll();

        for(int i=0; i<usr.size();i++)
        {
            User temp = usr.get(i);
            Log.d(TAG,temp.getFirstName()+"Task is completed\n Number of users tasks are completed: "+Integer.toString(usr.size()));
        }

        //check which users task is not completed
        RealmResults<User> usr2 = realm.where(User.class)
                .equalTo("task.isCompleted",false)
                .findAll();

        for(int i=0; i<usr2.size();i++)
        {
            User temp = usr2.get(i);
            Log.d(TAG,temp.getFirstName()+"Task is not completed\n Number of users tasks are not completed: "+Integer.toString(usr2.size()));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
