package com.deange.githubstatus.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;

import com.deange.githubstatus.R;
import com.deange.githubstatus.dagger.MockMode;
import com.deange.githubstatus.util.Unit;
import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class DevSettingsDialog {

  @MockMode private final Preference<Boolean> mockPreference;

  @BindView(R.id.dev_mock_mode) SwitchCompat mockSwitch;

  private final Subject<Unit> changes = PublishSubject.create();

  @Inject
  public DevSettingsDialog(@MockMode Preference<Boolean> mockPreference) {
    this.mockPreference = mockPreference;
  }

  public void show(@NonNull Context context) {
    AlertDialog dialog = new AlertDialog.Builder(context)
        .setView(R.layout.dev_settings)
        .setOnDismissListener(d -> onDismissed())
        .setPositiveButton(android.R.string.ok, null)
        .show();

    ButterKnife.bind(this, dialog);

    mockSwitch.setChecked(mockPreference.get());
  }

  private void onDismissed() {
    changes.onNext(Unit.INSTANCE);
  }

  @OnCheckedChanged(R.id.dev_mock_mode)
  void onMockModeToggled(boolean isChecked) {
    mockPreference.set(isChecked);
  }

  public Observable<Unit> onDevSettingsChanged() {
    return changes;
  }
}
