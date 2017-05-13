/*
 * Copyright 2014 Guidewire Software, Inc.
 */

package gw.lang.init;

import manifold.api.fs.IDirectory;
import gw.test.TestClass;

import java.util.Collections;
import java.io.File;
import manifold.api.fs.IFileSystem;
import manifold.api.fs.def.JavaDirectoryImpl;

/**
 * Created by IntelliJ IDEA.
 * User: akeefer
 * Date: May 17, 2010
 * Time: 3:14:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class GosuPathEntryTest extends TestClass {

  public void testConstructorThrowsIllegalArgumentExceptionIfRootIsNull() {
    try {
      new GosuPathEntry(null, Collections.<IDirectory>emptyList());
      fail();
    } catch (IllegalArgumentException e) {
      // Expected();
    }

  }

  public void testConstructorThrowsIllegalArgumentExceptionIfSrcsIsNull() {
    try {
      new GosuPathEntry( new JavaDirectoryImpl( new File( "foo/bar"), IFileSystem.CachingMode.NO_CACHING), null);
      fail();
    } catch (IllegalArgumentException e) {
      // Expected();
    }
  }

  public void testConstructorDoesNothingElseIfArgumentsAreValid() {
    new GosuPathEntry(new JavaDirectoryImpl(new File("foo/bar"), IFileSystem.CachingMode.NO_CACHING), Collections.<IDirectory>emptyList());
  }
}
