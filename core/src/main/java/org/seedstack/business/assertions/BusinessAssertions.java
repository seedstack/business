/**
 * Copyright (c) 2013-2015 by The SeedStack authors. All rights reserved.
 *
 * This file is part of SeedStack, An enterprise-oriented full development stack.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.business.assertions;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BigDecimalAssert;
import org.assertj.core.api.BooleanArrayAssert;
import org.assertj.core.api.BooleanAssert;
import org.assertj.core.api.ByteArrayAssert;
import org.assertj.core.api.ByteAssert;
import org.assertj.core.api.CharArrayAssert;
import org.assertj.core.api.CharSequenceAssert;
import org.assertj.core.api.CharacterAssert;
import org.assertj.core.api.Condition;
import org.assertj.core.api.DateAssert;
import org.assertj.core.api.DoubleArrayAssert;
import org.assertj.core.api.DoubleAssert;
import org.assertj.core.api.Fail;
import org.assertj.core.api.FileAssert;
import org.assertj.core.api.FloatArrayAssert;
import org.assertj.core.api.FloatAssert;
import org.assertj.core.api.InputStreamAssert;
import org.assertj.core.api.IntArrayAssert;
import org.assertj.core.api.IntegerAssert;
import org.assertj.core.api.IterableAssert;
import org.assertj.core.api.ListAssert;
import org.assertj.core.api.LongArrayAssert;
import org.assertj.core.api.LongAssert;
import org.assertj.core.api.MapAssert;
import org.assertj.core.api.ObjectArrayAssert;
import org.assertj.core.api.ObjectAssert;
import org.assertj.core.api.ShortArrayAssert;
import org.assertj.core.api.ShortAssert;
import org.assertj.core.api.StringAssert;
import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.filter.Filters;
import org.assertj.core.condition.AllOf;
import org.assertj.core.condition.AnyOf;
import org.assertj.core.condition.DoesNotHave;
import org.assertj.core.condition.Not;
import org.assertj.core.data.Index;
import org.assertj.core.data.MapEntry;
import org.assertj.core.data.Offset;
import org.assertj.core.groups.Properties;
import org.assertj.core.groups.Tuple;
import org.assertj.core.util.Files;
import org.assertj.core.util.FilesException;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author epo.jemba@ext.mpsa.com
 *
 */
public class BusinessAssertions {

	
	/**
	 * 
	 */
	protected BusinessAssertions() {
	}
	
	/**
	 * @param actual class to check
	 * @return the actual Assert
	 */
	public static BusinessClassAssert assertThat(Class<?> actual) {
		return new BusinessClassAssert(actual);
	}
	
	
	// Assertions inheritance
	

	  /**
	   * Creates a new instance of <code>{@link BigDecimalAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static BigDecimalAssert assertThat(BigDecimal actual) {
	    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link BooleanAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static BooleanAssert assertThat(boolean actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link BooleanAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static BooleanAssert assertThat(Boolean actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link BooleanArrayAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static BooleanArrayAssert assertThat(boolean[] actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link ByteAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static ByteAssert assertThat(byte actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link ByteAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static ByteAssert assertThat(Byte actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link ByteArrayAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static ByteArrayAssert assertThat(byte[] actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link CharacterAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static CharacterAssert assertThat(char actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link CharArrayAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static CharArrayAssert assertThat(char[] actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link CharacterAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static CharacterAssert assertThat(Character actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link IterableAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static <T> IterableAssert<T> assertThat(Iterable<T> actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link IterableAssert}</code>. The <code>{@link Iterator}</code> is first converted
	   * into an <code>{@link Iterable}</code>
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static <T> IterableAssert<T> assertThat(Iterator<T> actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link DoubleAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static DoubleAssert assertThat(double actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link DoubleAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static DoubleAssert assertThat(Double actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link DoubleArrayAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static DoubleArrayAssert assertThat(double[] actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link FileAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static FileAssert assertThat(File actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link InputStreamAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static InputStreamAssert assertThat(InputStream actual) {
	       return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link FloatAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static FloatAssert assertThat(float actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link FloatAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static FloatAssert assertThat(Float actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link FloatArrayAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static FloatArrayAssert assertThat(float[] actual) {
	  	    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link IntegerAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static IntegerAssert assertThat(int actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link IntArrayAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static IntArrayAssert assertThat(int[] actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link IntegerAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static IntegerAssert assertThat(Integer actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link ListAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static <T> ListAssert<T> assertThat(List<T> actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link LongAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static LongAssert assertThat(long actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link LongAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static LongAssert assertThat(Long actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link LongArrayAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static LongArrayAssert assertThat(long[] actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link ObjectAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static <T> ObjectAssert<T> assertThat(T actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link ObjectArrayAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static <T> ObjectArrayAssert<T> assertThat(T[] actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link MapAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static <K, V> MapAssert<K, V> assertThat(Map<K, V> actual) {
		    return Assertions.assertThat(actual);
	   }

	  /**
	   * Creates a new instance of <code>{@link ShortAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static ShortAssert assertThat(short actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link ShortAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static ShortAssert assertThat(Short actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link ShortArrayAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static ShortArrayAssert assertThat(short[] actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link CharSequenceAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static CharSequenceAssert assertThat(CharSequence actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link StringAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static StringAssert assertThat(String actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link DateAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion object.
	   */
	  public static DateAssert assertThat(Date actual) {
		    return Assertions.assertThat(actual);
	  }

	  /**
	   * Creates a new instance of <code>{@link ThrowableAssert}</code>.
	   * 
	   * @param actual the actual value.
	   * @return the created assertion Throwable.
	   */
	  public static ThrowableAssert assertThat(Throwable actual) {
		    return Assertions.assertThat(actual);
	  }

	  // -------------------------------------------------------------------------------------------------
	  // fail methods : not assertions but here to have a single entry point to all AssertJ features.
	  // -------------------------------------------------------------------------------------------------

	  /**
	   * Only delegate to {@link Fail#setRemoveAssertJRelatedElementsFromStackTrace(boolean)} so that Assertions offers a full
	   * feature entry point to all AssertJ Assert features (but you can use {@link Fail} if you prefer).
       *
       * @param removeAssertJRelatedElementsFromStackTrace remove AssertJ related elements from StackTrace
	   */
	  public static void setRemoveAssertJRelatedElementsFromStackTrace(boolean removeAssertJRelatedElementsFromStackTrace) {
	    Fail.setRemoveAssertJRelatedElementsFromStackTrace(removeAssertJRelatedElementsFromStackTrace);
	  }

	  /**
	   * Only delegate to {@link Fail#fail(String)} so that Assertions offers a full feature entry point to all Fest Assert
	   * features (but you can use Fail if you prefer).
       *
       * @param failureMessage the failure message
	   */
	  public static void fail(String failureMessage) {
	    Fail.fail(failureMessage);
	  }

	  /**
	   * Only delegate to {@link Fail#fail(String, Throwable)} so that Assertions offers a full feature entry point to all
	   * Fest Assert features (but you can use Fail if you prefer).
       *
       * @param failureMessage the failure message
       * @param realCause the real cause
	   */
	  public static void fail(String failureMessage, Throwable realCause) {
	    Fail.fail(failureMessage, realCause);
	  }

	  /**
	   * Only delegate to {@link Fail#failBecauseExceptionWasNotThrown(Class)} so that Assertions offers a full feature
	   * entry point to all Fest Assert features (but you can use Fail if you prefer).
       *
       * @param exceptionClass the exception class
	   */
	  public static void failBecauseExceptionWasNotThrown(Class<? extends Throwable> exceptionClass) {
	    Fail.failBecauseExceptionWasNotThrown(exceptionClass);
	  }

	  // ------------------------------------------------------------------------------------------------------
	  // properties methods : not assertions but here to have a single entry point to all Fest Assert features.
	  // ------------------------------------------------------------------------------------------------------

	  /**
	   * Only delegate to {@link Properties#extractProperty(String)} so that Assertions offers a full feature entry point to
	   * all Fest Assert features (but you can use {@link Properties} if you prefer).
	   * <p>
	   * Typical usage is to chain <code>extractProperty</code> with <code>from</code> method, see examples below :
	   * 
	   * <pre>
	   * // extract simple property values having a java standard type (here String)
	   * assertThat(extractProperty(&quot;name&quot;, String.class).from(fellowshipOfTheRing)).contains(&quot;Boromir&quot;, &quot;Gandalf&quot;, &quot;Frodo&quot;,
	   *     &quot;Legolas&quot;).doesNotContain(&quot;Sauron&quot;, &quot;Elrond&quot;);
	   * 
	   * // extracting property works also with user's types (here Race)
	   * assertThat(extractProperty(&quot;race&quot;, String.class).from(fellowshipOfTheRing)).contains(HOBBIT, ELF).doesNotContain(ORC);
	   * 
	   * // extract nested property on Race
	   * assertThat(extractProperty(&quot;race.name&quot;, String.class).from(fellowshipOfTheRing)).contains(&quot;Hobbit&quot;, &quot;Elf&quot;)
	   *     .doesNotContain(&quot;Orc&quot;);
	   * </pre>
       *
       * @param propertyName the property name
       * @param propertyType the property type
       * @return properties of T
	   */
	  public static <T> Properties<T> extractProperty(String propertyName, Class<T> propertyType) {
	    return Properties.extractProperty(propertyName, propertyType);
	  }

	  /**
	   * Only delegate to {@link Properties#extractProperty(String)} so that Assertions offers a full feature entry point to
	   * all Fest Assert features (but you can use {@link Properties} if you prefer).
	   * <p>
	   * Typical usage is to chain <code>extractProperty</code> with <code>from</code> method, see examples below :
	   * 
	   * <pre>
	   * // extract simple property values, as no type has been defined the extracted property will be considered as Object
	   * // to define the real property type (here String) use extractProperty(&quot;name&quot;, String.class) instead.
	   * assertThat(extractProperty(&quot;name&quot;).from(fellowshipOfTheRing)).contains(&quot;Boromir&quot;, &quot;Gandalf&quot;, &quot;Frodo&quot;, &quot;Legolas&quot;)
	   *     .doesNotContain(&quot;Sauron&quot;, &quot;Elrond&quot;);
	   * 
	   * // extracting property works also with user's types (here Race), even though it will be considered as Object
	   * // to define the real property type (here String) use extractProperty(&quot;name&quot;, Race.class) instead.
	   * assertThat(extractProperty(&quot;race&quot;).from(fellowshipOfTheRing)).contains(HOBBIT, ELF).doesNotContain(ORC);
	   * 
	   * // extract nested property on Race
	   * assertThat(extractProperty(&quot;race.name&quot;).from(fellowshipOfTheRing)).contains(&quot;Hobbit&quot;, &quot;Elf&quot;).doesNotContain(&quot;Orc&quot;);
	   * </pre>
       *
       * @param propertyName the property name
       * @return properties of objects
       */
	  public static Properties<Object> extractProperty(String propertyName) {
	    return Properties.extractProperty(propertyName);
	  }

	  /**
	   * Utility method to build nicely a {@link Tuple} when working with {@link IterableAssert#extracting(String...)} or
	   * {@link ObjectArrayAssert#extracting(String...)}
	   * 
	   * @param values the values stored in the {@link Tuple}
	   * @return the built {@link Tuple}
	   */
	  public static Tuple tuple(Object... values) {
	    return Tuple.tuple(values);
	  }

	  // ------------------------------------------------------------------------------------------------------
	  // Data utility methods : not assertions but here to have a single entry point to all Fest Assert features.
	  // ------------------------------------------------------------------------------------------------------

	  /**
	   * Only delegate to {@link MapEntry#entry(Object, Object)} so that Assertions offers a full feature entry point to all
	   * Fest Assert features (but you can use {@link MapEntry} if you prefer).
	   * <p>
	   * Typical usage is to call <code>entry</code> in MapAssert <code>contains</code> assertion, see examples below :
	   * 
	   * <pre>
	   * assertThat(ringBearers).contains(entry(oneRing, frodo), entry(nenya, galadriel));
	   * </pre>
       *
       * @param key the key
       * @param value the value
       * @return the map entry
	   */
	  public static MapEntry entry(Object key, Object value) {
	    return MapEntry.entry(key, value);
	  }

	  /**
	   * Only delegate to {@link Index#atIndex(int)} so that Assertions offers a full feature entry point to all Fest Assert
	   * features (but you can use {@link Index} if you prefer).
	   * <p>
	   * Typical usage :
	   * 
	   * <pre>
	   * List&lt;Ring&gt; elvesRings = newArrayList(vilya, nenya, narya);
	   * assertThat(elvesRings).contains(vilya, atIndex(0)).contains(nenya, atIndex(1)).contains(narya, atIndex(2));
	   * </pre>
       * @param index the index
       * @return the index
	   */
	  public static Index atIndex(int index) {
	    return Index.atIndex(index);
	  }

	  /**
	   * Only delegate to {@link Offset#offset(Double)} so that Assertions offers a full feature entry point to all Fest
	   * Assert features (but you can use {@link Offset} if you prefer).
	   * <p>
	   * Typical usage :
	   * 
	   * <pre>
	   * assertThat(8.1).isEqualTo(8.0, offset(0.1));
	   * </pre>
       *
       * @param value the double
       * @return the Double offset
	   */
	  public static Offset<Double> offset(Double value) {
	    return Offset.offset(value);
	  }

	  /**
	   * Only delegate to {@link Offset#offset(Float)} so that Assertions offers a full feature entry point to all Fest
	   * Assert features (but you can use {@link Offset} if you prefer).
	   * <p>
	   * Typical usage :
	   * 
	   * <pre>
	   * assertThat(8.2f).isEqualTo(8.0f, offset(0.2f));
	   * </pre>
       *
       * @param value the float
       * @return the float offset
	   */
	  public static Offset<Float> offset(Float value) {
	    return Offset.offset(value);
	  }

	  // ------------------------------------------------------------------------------------------------------
	  // Condition methods : not assertions but here to have a single entry point to all Fest Assert features.
	  // ------------------------------------------------------------------------------------------------------

	  /**
	   * Creates a new <code>{@link AllOf}</code>
	   * 
	   * @param <T> the type of object the given condition accept.
	   * @param conditions the conditions to evaluate.
	   * @return the created {@code AnyOf}.
	   * @throws NullPointerException If the given array is {@code null} or
       * if any of the elements in the given array is {@code null}.
	   */
	  public static <T> Condition<T> allOf(Condition<? super T>... conditions) {
	    return AllOf.allOf(conditions);
	  }

	  /**
	   * Creates a new <code>{@link AllOf}</code>
	   * 
	   * @param <T> the type of object the given condition accept.
	   * @param conditions the conditions to evaluate.
	   * @return the created {@code AnyOf}.
	   * @throws NullPointerException if the given iterable is {@code null} or if any of the elements in the given
       * iterable is {@code null}.
	   */
	  public static <T> Condition<T> allOf(Iterable<? extends Condition<? super T>> conditions) {
	    return AllOf.allOf(conditions);
	  }

	  /**
	   * Only delegate to {@link AnyOf#anyOf(Condition...)} so that Assertions offers a full feature entry point to all Fest
	   * Assert features (but you can use {@link AnyOf} if you prefer).
	   * <p>
	   * Typical usage (<code>jedi</code> and <code>sith</code> are {@link Condition}) :
	   * 
	   * <pre>
	   * assertThat(&quot;Vader&quot;).is(anyOf(jedi, sith));
	   * </pre>
       * @param conditions the condition
       * @return the new "any of" condition
	   */
	  public static <T> Condition<T> anyOf(Condition<? super T>... conditions) {
	    return AnyOf.anyOf(conditions);
	  }

	  /**
	   * Creates a new <code>{@link AnyOf}</code>
	   * 
	   * @param <T> the type of object the given condition accept.
	   * @param conditions the conditions to evaluate.
	   * @return the created {@code AnyOf}.
	   * @throws NullPointerException if the given iterable is {@code null} or if any of the elements in the given
       * iterable is {@code null}.
	   */
	  public static <T> Condition<T> anyOf(Iterable<? extends Condition<? super T>> conditions) {
	    return AnyOf.anyOf(conditions);
	  }

	  /**
	   * Creates a new </code>{@link DoesNotHave}</code>.
	   * 
	   * @param condition the condition to inverse.
	   * @return The Not condition created.
	   */
	  public static <T> DoesNotHave<T> doesNotHave(Condition<? super T> condition) {
	    return DoesNotHave.doesNotHave(condition);
	  }

	  /**
	   * Creates a new </code>{@link Not}</code>.
	   * 
	   * @param condition the condition to inverse.
	   * @return The Not condition created.
	   */
	  public static <T> Not<T> not(Condition<? super T> condition) {
	    return Not.not(condition);
	  }

	  // --------------------------------------------------------------------------------------------------
	  // Filter methods : not assertions but here to have a single entry point to all Fest Assert features.
	  // --------------------------------------------------------------------------------------------------

	  /**
	   * Only delegate to {@link Filters#filter(Object[])} so that Assertions offers a full feature entry point to all Fest
	   * Assert features (but you can use {@link Filters} if you prefer).
	   * <p>
	   * Note that the given array is not modified, the filters are performed on an {@link Iterable} copy of the array.
	   * <p>
	   * Typical usage with {@link Condition} :
	   * 
	   * <pre>
	   * assertThat(filter(players).being(potentialMVP).get()).containsOnly(james, rose);
	   * </pre>
	   * 
	   * and with filter language based on java bean property :
	   * 
	   * <pre>
	   * assertThat(filter(players).with(&quot;pointsPerGame&quot;).greaterThan(20).and(&quot;assistsPerGame&quot;).greaterThan(7).get())
	   *     .containsOnly(james, rose);
	   * </pre>
       * @param array the array
       * @return the filters
	   */
	  public static <E> Filters<E> filter(E[] array) {
	    return Filters.filter(array);
	  }

	  /**
	   * Only delegate to {@link Filters#filter(Object[])} so that Assertions offers a full feature entry point to all Fest
	   * Assert features (but you can use {@link Filters} if you prefer).
	   * <p>
	   * Note that the given {@link Iterable} is not modified, the filters are performed on a copy.
	   * <p>
	   * Typical usage with {@link Condition} :
	   * 
	   * <pre>
	   * assertThat(filter(players).being(potentialMVP).get()).containsOnly(james, rose);
	   * </pre>
	   * 
	   * and with filter language based on java bean property :
	   * 
	   * <pre>
	   * assertThat(filter(players).with(&quot;pointsPerGame&quot;).greaterThan(20).and(&quot;assistsPerGame&quot;).greaterThan(7).get())
	   *     .containsOnly(james, rose);
	   * </pre>
       * @param iterableToFilter the iterableToFilter
       * @return the filters
	   */
	  public static <E> Filters<E> filter(Iterable<E> iterableToFilter) {
	    return Filters.filter(iterableToFilter);
	  }

	  // --------------------------------------------------------------------------------------------------
	  // File methods : not assertions but here to have a single entry point to all Fest Assert features.
	  // --------------------------------------------------------------------------------------------------

	  /**
	   * Loads the text content of a file, so that it can be passed to {@link #assertThat(String)}.
	   * <p>
	   * Note that this will load the entire file in memory; for larger files, there might be a more efficient alternative
	   * with {@link #assertThat(File)}.
	   * </p>
	   * 
	   * @param file the file.
	   * @param charset the character set to use.
	   * @return the content of the file.
	   * @throws NullPointerException if the given charset is {@code null}.
	   * @throws FilesException if an I/O exception occurs.
	   */
	  public static String contentOf(File file, Charset charset) {
	    return Files.contentOf(file, charset);
	  }

	  /**
	   * Loads the text content of a file, so that it can be passed to {@link #assertThat(String)}.
	   * <p>
	   * Note that this will load the entire file in memory; for larger files, there might be a more efficient alternative
	   * with {@link #assertThat(File)}.
	   * </p>
	   * 
	   * @param file the file.
	   * @param charsetName the name of the character set to use.
	   * @return the content of the file.
	   * @throws IllegalArgumentException if the given character set is not supported on this platform.
	   * @throws FilesException if an I/O exception occurs.
	   */
	  public static String contentOf(File file, String charsetName) {
	    return Files.contentOf(file, charsetName);
	  }

	  /**
	   * Loads the text content of a file with the default character set, so that it can be passed to
	   * {@link #assertThat(String)}.
	   * <p>
	   * Note that this will load the entire file in memory; for larger files, there might be a more efficient alternative
	   * with {@link #assertThat(File)}.
	   * </p>
	   * 
	   * @param file the file.
	   * @return the content of the file.
	   * @throws FilesException if an I/O exception occurs.
	   */
	  public static String contentOf(File file) {
	    return Files.contentOf(file, Charset.defaultCharset());
	  }
	
	
	
	
}
