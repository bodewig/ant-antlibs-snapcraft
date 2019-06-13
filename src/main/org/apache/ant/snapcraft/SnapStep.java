/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.apache.ant.snapcraft;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.ExecTask;

/**
 * Builds a snap.
 */
public class SnapStep extends ExecTask {
    private File output;

    public SnapStep() {
        setExecutable("snapcraft");
    }

    public void setOutputFile(File output) {
        this.output = output;
    }

    public void execute() throws BuildException {
        createArg().setValue("snap");
        if (output != null) {
            createArg().setValue("-o");
            createArg().setFile(output);
        }
        super.execute();
    }
}
