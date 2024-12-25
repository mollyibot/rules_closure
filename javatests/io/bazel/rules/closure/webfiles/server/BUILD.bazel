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

load("@rules_java//java:defs.bzl", "java_test")

licenses(["notice"])

java_test(
    name = "ServerTestSuite",
    size = "small",
    srcs = glob(["*.java"]),
    deps = [
        "//java/io/bazel/rules/closure:webpath",
        "//java/io/bazel/rules/closure/http",
        "//java/io/bazel/rules/closure/webfiles:build_info_java_proto",
        "//java/io/bazel/rules/closure/webfiles/server",
        "//java/io/bazel/rules/closure/webfiles/server:build_info_java_proto",
        "@maven//:com_google_code_findbugs_jsr305",
        "@maven//:com_google_dagger_dagger",
        "@maven//:com_google_guava_guava",
        "@maven//:com_google_guava_guava_testlib",
        "@maven//:com_google_jimfs_jimfs",
        "@maven//:com_google_truth_truth",
        "@maven//:junit_junit",
        "@maven//:org_mockito_mockito_core",
    ],
)
