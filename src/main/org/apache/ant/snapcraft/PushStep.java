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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.ExecTask;

/**
 * Pushes a snap.
 */
public class PushStep extends ExecTask {
    private File snap;
    private final List<Channel> channels = new ArrayList<>();

    public PushStep() {
        setExecutable("snapcraft");
    }

    public void setSnap(File snap) {
        this.snap = snap;
    }

    public void setChannel(String channel) {
        addChannel(new Channel(channel));
    }
   
    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    public void execute() throws BuildException {
        if (snap == null) {
            throw new BuildException("the snap attribute is required");
        }
        if (!snap.isFile()) {
            throw new BuildException(snap + " doesn't exist or is a directory");
        }
        createArg().setValue("push");
        createArg().setFile(snap);
        if (!channels.isEmpty()) {
            createArg().setValue("--release");
            createArg()
                .setValue(channels.stream().map(Channel::getChannel)
                          .collect(Collectors.joining(",")));
        }
        super.execute();
    }

    public static class Channel {
        private String channel;
        public Channel() {
        }
        public Channel(String channel) {
            this.channel = channel;
        }
        public void setName(String name) {
            this.channel = channel;
        }
        public String getChannel() {
            return channel;
        }
    }
}
