/*
 * Copyright 2016 The Closure Rules Authors. All rights reserved.
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

package com.google.javascript.jscomp;

import com.google.common.collect.ImmutableList;
import com.google.javascript.jscomp.NodeTraversal.Callback;
import com.google.javascript.jscomp.ijs.ConvertToTypedInterface;
import com.google.javascript.jscomp.lint.CheckDuplicateCase;
import com.google.javascript.jscomp.lint.CheckEmptyStatements;
import com.google.javascript.jscomp.lint.CheckEnums;
import com.google.javascript.jscomp.lint.CheckInterfaces;
import com.google.javascript.jscomp.lint.CheckJSDocStyle;
import com.google.javascript.jscomp.lint.CheckMissingSemicolon;
import com.google.javascript.jscomp.lint.CheckPrimitiveAsObject;
import com.google.javascript.jscomp.lint.CheckPrototypeProperties;
import com.google.javascript.jscomp.lint.CheckProvidesSorted;
import com.google.javascript.jscomp.lint.CheckRequiresSorted;
import com.google.javascript.jscomp.lint.CheckUnusedLabels;
import com.google.javascript.jscomp.lint.CheckUselessBlocks;

final class JsCheckerPassConfig extends PassConfig.PassConfigDelegate {

  private final JsCheckerState state;
  private final PassListBuilder checks;

  JsCheckerPassConfig(JsCheckerState state, CompilerOptions options) {
    super(new DefaultPassConfig(options));
    this.state = state;
    this.checks = new PassListBuilder(options);

    this.checks.maybeAdd(gatherModuleMetadataPass());
    this.checks.maybeAdd(earlyLintChecks());
    this.checks.maybeAdd(scopedAliases());
    this.checks.maybeAdd(lateLintChecks());
    this.checks.maybeAdd(ijsGeneration());
    this.checks.maybeAdd(whitespaceWrapGoogModules());
  }

  @Override
  protected PassListBuilder getChecks() {
    return checks;
  }

  @Override
  protected PassListBuilder getOptimizations(PassConfig.OptimizationPasses optimizationPasses) {
    return new PassListBuilder(options);
  }

  private PassFactory gatherModuleMetadataPass() {
    return PassFactory.builder()
        .setName("gather module metadata")
        .setInternalFactory(
            (compiler) ->
                new GatherModuleMetadata(
                    compiler,
                    compiler.getOptions().getProcessCommonJSModules(),
                    compiler.getOptions().getModuleResolutionMode()))
        .build();
  }

  private PassFactory earlyLintChecks() {
    return PassFactory.builder()
        .setName("earlyLintChecks")
        .setInternalFactory(
            (compiler) ->
                new CombinedCompilerPass(
                    compiler,
                    ImmutableList.<Callback>of(
                        new CheckDuplicateCase(compiler),
                        new CheckEmptyStatements(compiler),
                        new CheckEnums(compiler),
                        new CheckJSDocStyle(compiler),
                        new CheckJSDoc(compiler),
                        new CheckMissingSemicolon(compiler),
                        new CheckSuper(compiler),
                        new CheckPrimitiveAsObject(compiler),
                        new CheckProvidesSorted(CheckProvidesSorted.Mode.COLLECT_AND_REPORT),
                        new CheckRequiresSorted(CheckRequiresSorted.Mode.COLLECT_AND_REPORT),
                        new CheckUnusedLabels(compiler),
                        new CheckUselessBlocks(compiler),
                        new ClosureCheckModule(compiler, compiler.getModuleMetadataMap()),
                        new CheckSetTestOnly(state, compiler),
                        new CheckStrictDeps.FirstPass(state, compiler))))
        .build();
  }

  private PassFactory scopedAliases() {
    return PassFactory.builder()
        .setName("scopedAliases")
        .setInternalFactory(
            (compiler) ->
                new ScopedAliases(
                    compiler,
                    /*preprocessorSymbolTable=*/ null,
                    compiler.getOptions().getAliasTransformationHandler()))
        .build();
  }

  private PassFactory lateLintChecks() {
    return PassFactory.builder()
        .setName("lateLintChecks")
        .setInternalFactory(
            (compiler) ->
                new CombinedCompilerPass(
                    compiler,
                    ImmutableList.<Callback>of(
                        new CheckInterfaces(compiler),
                        new CheckPrototypeProperties(compiler),
                        new CheckStrictDeps.SecondPass(state, compiler))))
        .build();
  }

  private PassFactory ijsGeneration() {
    return PassFactory.builder()
        .setName("ijsGeneration")
        .setInternalFactory((compiler) -> new ConvertToTypedInterface(compiler))
        .build();
  }

  private PassFactory whitespaceWrapGoogModules() {
    return PassFactory.builder()
        .setName("whitespaceWrapGoogModules")
        .setInternalFactory(WhitespaceWrapGoogModules::new)
        .build();
  }
}
