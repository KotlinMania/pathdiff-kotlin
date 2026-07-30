// port-lint: source lib.rs
// Copyright 2012-2015 The Rust Project Developers. See the COPYRIGHT
// file at the top-level directory of this distribution and at
// http://rust-lang.org/COPYRIGHT.
//
// Licensed under the Apache License, Version 2.0 <LICENSE-APACHE or
// http://www.apache.org/licenses/LICENSE-2.0> or the MIT license
// <LICENSE-MIT or http://opensource.org/licenses/MIT>, at your
// option. This file may not be copied, modified, or distributed
// except according to those terms.

// Adapted from rustc's path relative routine.
// Reference revision: e1d0de82cc40b666b88d4a6d2c9dcbc81d7ed27f.

package io.github.kotlinmania.pathdiff

private sealed class Component {
    object RootDir : Component()

    object CurDir : Component()

    object ParentDir : Component()

    data class Normal(
        val name: String,
    ) : Component()

    fun asString(): String =
        when (this) {
            is RootDir -> "/"
            is CurDir -> "."
            is ParentDir -> ".."
            is Normal -> name
        }
}

private fun String.isAbsolutePath(): Boolean = startsWith('/')

private fun String.pathComponents(): List<Component> {
    if (isEmpty()) return emptyList()
    val result = mutableListOf<Component>()
    var rest: String = this
    if (rest.startsWith('/')) {
        result.add(Component.RootDir)
        rest = rest.trimStart('/')
    }
    for (seg in rest.split('/')) {
        if (seg.isEmpty()) continue
        when (seg) {
            "." -> if (result.isEmpty()) result.add(Component.CurDir)
            ".." -> result.add(Component.ParentDir)
            else -> result.add(Component.Normal(seg))
        }
    }
    return result
}

private fun List<Component>.toPathString(): String {
    val sb = StringBuilder()
    for (c in this) {
        if (sb.isNotEmpty() && sb.last() != '/') sb.append('/')
        sb.append(c.asString())
    }
    return sb.toString()
}

private fun <T> Iterator<T>.nextOrNull(): T? = if (hasNext()) next() else null

/**
 * Construct a relative path from a provided base directory path to the provided path.
 *
 * ```
 * diffPaths("/foo/bar",      "/foo/bar/baz")  // ".."
 * diffPaths("/foo/bar/baz",  "/foo/bar")      // "baz"
 * diffPaths("/foo/bar/quux", "/foo/bar/baz")  // "../quux"
 * diffPaths("/foo/bar/baz",  "/foo/bar/quux") // "../baz"
 * diffPaths("/foo/bar",      "/foo/bar/quux") // ".."
 *
 * diffPaths("/foo/bar",      "baz")           // "/foo/bar"
 * diffPaths("/foo/bar",      "/baz")          // "../foo/bar"
 * diffPaths("foo",           "bar")           // "../foo"
 * ```
 */
public fun diffPaths(path: String, base: String): String? {
    val pathAbs = path.isAbsolutePath()
    val baseAbs = base.isAbsolutePath()

    if (pathAbs != baseAbs) {
        return if (pathAbs) path else null
    }

    val ita = path.pathComponents().iterator()
    val itb = base.pathComponents().iterator()
    val comps = mutableListOf<Component>()
    loop@ while (true) {
        val a = ita.nextOrNull()
        val b = itb.nextOrNull()
        if (a == null && b == null) break
        if (b == null) {
            comps.add(a!!)
            for (rest in ita) comps.add(rest)
            break
        }
        if (a == null) {
            comps.add(Component.ParentDir)
            continue
        }
        when {
            comps.isEmpty() && a == b -> {
            }
            b == Component.CurDir -> comps.add(a)
            b == Component.ParentDir -> return null
            else -> {
                comps.add(Component.ParentDir)
                for (unused in itb) {
                    comps.add(Component.ParentDir)
                }
                comps.add(a)
                for (rest in ita) comps.add(rest)
                break
            }
        }
    }
    return comps.toPathString()
}

/**
 * Construct a relative UTF-8 path from a provided base directory path to the provided path.
 *
 * Kotlin strings are Unicode, so this variant is available directly.
 *
 * ```
 * diffUtf8Paths("/foo/bar",      "/foo/bar/baz")  // ".."
 * diffUtf8Paths("/foo/bar/baz",  "/foo/bar")      // "baz"
 * diffUtf8Paths("/foo/bar/quux", "/foo/bar/baz")  // "../quux"
 * diffUtf8Paths("/foo/bar/baz",  "/foo/bar/quux") // "../baz"
 * diffUtf8Paths("/foo/bar",      "/foo/bar/quux") // ".."
 *
 * diffUtf8Paths("/foo/bar",      "baz")           // "/foo/bar"
 * diffUtf8Paths("/foo/bar",      "/baz")          // "../foo/bar"
 * diffUtf8Paths("foo",           "bar")           // "../foo"
 * ```
 */
public fun diffUtf8Paths(path: String, base: String): String? {
    val pathAbs = path.isAbsolutePath()
    val baseAbs = base.isAbsolutePath()

    if (pathAbs != baseAbs) {
        return if (pathAbs) path else null
    }

    val ita = path.pathComponents().iterator()
    val itb = base.pathComponents().iterator()
    val comps = mutableListOf<Component>()
    loop@ while (true) {
        val a = ita.nextOrNull()
        val b = itb.nextOrNull()
        if (a == null && b == null) break
        if (b == null) {
            comps.add(a!!)
            for (rest in ita) comps.add(rest)
            break
        }
        if (a == null) {
            comps.add(Component.ParentDir)
            continue
        }
        when {
            comps.isEmpty() && a == b -> {
            }
            b == Component.CurDir -> comps.add(a)
            b == Component.ParentDir -> return null
            else -> {
                comps.add(Component.ParentDir)
                for (unused in itb) {
                    comps.add(Component.ParentDir)
                }
                comps.add(a)
                for (rest in ita) comps.add(rest)
                break
            }
        }
    }
    return comps.toPathString()
}
