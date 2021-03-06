package com.robotfriendgames.ld30.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.robotfriendgames.ld30.LD30Game;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(512, 600);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new LD30Game();
        }
}
