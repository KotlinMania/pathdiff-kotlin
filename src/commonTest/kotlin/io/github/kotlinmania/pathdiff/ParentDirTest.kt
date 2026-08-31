// port-lint: tests pathdiff/src/lib.rs
package io.github.kotlinmania.pathdiff

import kotlin.test.Test
import kotlin.test.assertEquals

class ParentDirTest {
    @Test
    fun testParentDirDiff() {
        assertEquals("..", diffPaths("/a/b", "/a/b/c"))
        assertEquals("..", diffUtf8Paths("/a/b", "/a/b/c"))
        assertEquals("c", diffPaths("/a/b/c", "/a/b"))
        assertEquals("c", diffUtf8Paths("/a/b/c", "/a/b"))
    }
}
