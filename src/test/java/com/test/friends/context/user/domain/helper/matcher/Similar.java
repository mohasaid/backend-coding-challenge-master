package com.test.friends.context.user.domain.helper.matcher;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.unitils.reflectionassert.ReflectionComparator;
import org.unitils.reflectionassert.comparator.Comparator;
import org.unitils.reflectionassert.comparator.impl.LenientOrderCollectionComparator;
import org.unitils.reflectionassert.comparator.impl.MapComparator;
import org.unitils.reflectionassert.comparator.impl.ObjectComparator;
import org.unitils.reflectionassert.comparator.impl.SimpleCasesComparator;
import org.unitils.reflectionassert.difference.Difference;
import org.unitils.reflectionassert.report.impl.DefaultDifferenceReport;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public final class Similar<T> extends TypeSafeDiagnosingMatcher<T> {

  private final T object;
  private final ReflectionComparator comparator;

  private Similar(T object) {
    this.object = object;
    this.comparator = comparator();
  }

  @Factory
  public static <T> Matcher<T> similar(T target) {
    return new Similar<>(target);
  }

  private static ReflectionComparator comparator() {
    List<Comparator> comparatorChain = new ArrayList<>();
    comparatorChain.add(new SimpleCasesComparator());
    comparatorChain.add(new LenientOrderCollectionComparator());
    comparatorChain.add(new MapComparator());
    comparatorChain.add(new ObjectComparator());

    return new ReflectionComparator(comparatorChain);
  }

  public static <T> T similarTo(T value) {
    return argThat(new Similar<>(value));
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("reflectively deep equal to: " + getStringRepresentation(object));
  }

  @Override
  protected boolean matchesSafely(T item, Description mismatchDescription) {
    boolean result = true;
    if (!comparator.isEqual(object, item)) {
      result = false;
      Difference difference = comparator.getDifference(object, item);
      String message = new DefaultDifferenceReport().createReport(difference);
      mismatchDescription.appendText("was: " + message);
    }

    return result;
  }

  private String getStringRepresentation(Object o) {
    return new ReflectionToStringBuilder(o, ToStringStyle.MULTI_LINE_STYLE).toString();
  }
}
