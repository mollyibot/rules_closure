load("//closure:repositories.bzl", "rules_closure_dependencies", "rules_closure_toolchains")

def _non_module_dependencies_impl(_ctx):
    rules_closure_dependencies()
    rules_closure_toolchains()

non_module_dependencies = module_extension(
    implementation = _non_module_dependencies_impl,
)
