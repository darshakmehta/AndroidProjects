package mad.com.hw06;

import android.app.Application;

import com.facebook.stetho.Stetho;

import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;



public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
        //Realm.setDefaultConfiguration(config);

        Realm.init(this);
       /* Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                        .withDescendingOrder()
                        .withLimit(1000)
                        .databaseNamePattern(Pattern.compile(".+\\.realm"))
                        .build())
                .build());*/
    }
}
