# Copyright 2017 The Closure Rules Authors. All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

load("@rules_java//java:defs.bzl", "java_library")

licenses(["notice"])

java_library(
    name = "dagger",
    exported_plugins = [":dagger_plugin"],
    exports = [
        "@maven//:com_google_dagger_dagger",
    ],
)

java_plugin(
    name = "dagger_plugin",
    generates_api = 1,
    processor_class = "dagger.internal.codegen.ComponentProcessor",
    deps = [
        #        "@maven//:com_google_dagger_dagger",
        "@maven//:com_google_dagger_dagger_compiler",
        #        "@maven//:com_google_dagger_dagger_producers",
    ],
)

java_library(
    name = "testing",
    testonly = 1,
    srcs = glob(["*.java"]),
    visibility = ["//:__subpackages__"],
    deps = [
        ":dagger",
        "//java/io/bazel/rules/closure/worker",
        "@google_bazel_common//third_party/java/auto:value",
        "@maven//:com_google_dagger_dagger",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_truth_truth",
        "@maven//:javax_inject_javax_inject",
    ],
)
