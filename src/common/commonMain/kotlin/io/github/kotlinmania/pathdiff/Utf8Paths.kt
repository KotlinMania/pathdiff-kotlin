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

/**
 * Construct a relative UTF-8 path from a provided base directory path to the provided path.
 *
 * Requires the `camino` feature in upstream Rust; provided directly in Kotlin.
 *
 * ```kotlin
 * assertEquals("..", diffUtf8Paths("/foo/bar", "/foo/bar/baz"))
 * assertEquals("baz", diffUtf8Paths("/foo/bar/baz", "/foo/bar"))
 * assertEquals("../quux", diffUtf8Paths("/foo/bar/quux", "/foo/bar/baz"))
 * assertEquals("../baz", diffUtf8Paths("/foo/bar/baz", "/foo/bar/quux"))
 * assertEquals("..", diffUtf8Paths("/foo/bar", "/foo/bar/quux"))
 *
 * assertEquals("/foo/bar", diffUtf8Paths("/foo/bar", "baz"))
 * assertEquals("../foo/bar", diffUtf8Paths("/foo/bar", "/baz"))
 * assertEquals("../foo", diffUtf8Paths("foo", "bar"))
 *
 * assertEquals("baz", diffUtf8Paths("/foo/bar/baz", "/foo/bar"))
 * assertEquals("baz", diffUtf8Paths("/foo/bar/baz", "/foo/bar"))
 * ```
 */
public fun diffUtf8Paths(path: String, base: String): String? = diffPaths(path, base)
