/*
 * Copyright 2016 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.triad;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * An utility class that retrieves the ActivityComponent and Presenter using a Context instance.
 */
public class TriadUtil {

    @NonNull
    public static <ActivityComponent> ActivityComponent findActivityComponent(@NonNull final Context context, @NonNull final View view) {
        //noinspection ConstantConditions
        if (view.isInEditMode()) return null;

        Context baseContext = context;
        while (!(baseContext instanceof Activity) && baseContext instanceof ContextWrapper) {
            baseContext = ((ContextWrapper) baseContext).getBaseContext();
        }

        //noinspection unchecked
        return ((ActivityComponentProvider<ActivityComponent>) baseContext).getActivityComponent();
    }

    @NonNull
    public static <P extends Presenter<?, ?>> P findPresenter(@NonNull final Context context, final View view) {
        //noinspection ConstantConditions
        if (view.isInEditMode()) return null;

        Context baseContext = context;
        while (!(baseContext instanceof Activity) && baseContext instanceof ContextWrapper) {
            baseContext = ((ContextWrapper) baseContext).getBaseContext();
        }

        try {
            return (P) ((ScreenProvider<?>) baseContext).getCurrentScreen().getPresenter(view.getId());
        } catch (Throwable t) {
            PresenterCreationFailedError error =
                  new PresenterCreationFailedError(String.format("Could not create presenter for:\n    %s\nCaused by: %s", view, t), t);
            error.setStackTrace(t.getStackTrace());
            throw error;
        }
    }

    private static class PresenterCreationFailedError extends Error {

        PresenterCreationFailedError(final String message) {
            super(message);
        }

        PresenterCreationFailedError(final String message, final Throwable throwable) {
            super(message, throwable);
        }
    }
}
