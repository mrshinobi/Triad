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

package com.nhaarman.triad.tests;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.nhaarman.triad.Triad;
import com.nhaarman.triad.TriadActivity;
import com.nhaarman.triad.tests.firstscreen.FirstScreen;

public class TestActivity extends TriadActivity<ApplicationComponent, ActivityComponent> {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate " + getTriad());
        System.out.println(getTriad().getBackstack());
        getTriad().startWith(new FirstScreen());
    }

    @NonNull
    @Override
    protected ActivityComponent createActivityComponent() {
        return new ActivityComponent();
    }

    @NonNull
    @Override
    public Triad getTriad() {
        return super.getTriad();
    }
}
