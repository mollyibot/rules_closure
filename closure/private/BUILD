# Copyright 2016 The Closure Rules Authors. All rights reserved.
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

load("@bazel_skylib//:bzl_library.bzl", "bzl_library")
load("//closure/compiler:closure_base_js_library.bzl", "closure_base_js_library")

package(default_visibility = ["//visibility:public"])

licenses(["notice"])

exports_files(["unusable_type.js"])

closure_base_js_library(
    name = "base_lib",
    # TODO(goktug): replace with base.js from compiler when included in the artifact.
    srcs = ["base.js"],
)

bzl_library(
    name = "defs_bzl",
    srcs = ["defs.bzl"],
)

bzl_library(
    name = "files_equal_test_bzl",
    srcs = ["files_equal_test.bzl"],
)
