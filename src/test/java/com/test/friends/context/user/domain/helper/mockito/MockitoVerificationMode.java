package com.test.friends.context.user.domain.helper.mockito;

import org.mockito.verification.VerificationMode;

import static org.mockito.internal.verification.VerificationModeFactory.times;

public final class MockitoVerificationMode {
  public static VerificationMode once() {
    return times(1);
  }
}
