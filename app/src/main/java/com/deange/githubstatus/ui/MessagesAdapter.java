package com.deange.githubstatus.ui;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.deange.githubstatus.R;
import com.deange.githubstatus.controller.GithubController;
import com.deange.githubstatus.model.Message;
import com.deange.githubstatus.model.Response;
import com.deange.githubstatus.model.State;
import com.deange.githubstatus.util.Formatter;
import com.deange.githubstatus.util.SimpleDiffCallback;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

import static com.deange.githubstatus.MainApplication.component;
import static java.util.Collections.emptyList;

public class MessagesAdapter
    extends RecyclerView.Adapter<MessagesAdapter.VH> {

  @Inject GithubController runner;

  private final Context context;
  private final LayoutInflater inflater;
  @NonNull private List<Message> messages = emptyList();

  public MessagesAdapter(Context context) {
    this.context = context;
    inflater = LayoutInflater.from(context);

    component(this.context).inject(this);
  }

  @Override
  public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    return new VH(inflater.inflate(R.layout.list_item_message, parent, false));
  }

  @Override
  public void onBindViewHolder(VH holder, int position) {
    Message message = this.messages.get(position);
    State state = message.state();
    int color = ContextCompat.getColor(context, state.getColorResId());

    ((LinearLayout.LayoutParams) holder.mTitle.getLayoutParams()).weight = state.getWeight();

    holder.mTitle.setBackgroundColor(color);
    holder.mTitle.setText(context.getString(state.getTitleResId()).toLowerCase());
    holder.mBody.setText(message.bodyForNotification(context));
    holder.mDate.setText(Formatter.formatLocalDateTime(message.createdOn()));
  }

  @Override
  public int getItemCount() {
    return messages.size();
  }

  @CheckResult
  public Disposable refreshStatus() {
    return runner.getStatus().subscribe(this::onResponseReceived, this::onResponseFailed);
  }

  private void onResponseReceived(Response response) {
    setMessages(response.messages());
  }

  private void onResponseFailed(Throwable throwable) {
    setMessages(emptyList());
  }

  void setMessages(@NonNull List<Message> messages) {
    List<Message> oldMessages = this.messages;
    this.messages = messages;

    DiffUtil.Callback callback = new SimpleDiffCallback<>(
        oldMessages, this.messages,
        (m1, m2) -> m1.id() == m2.id());

    DiffUtil.calculateDiff(callback, false).dispatchUpdatesTo(this);
  }

  static class VH extends RecyclerView.ViewHolder {
    @BindView(R.id.message_title) TextView mTitle;
    @BindView(R.id.message_body) TextView mBody;
    @BindView(R.id.message_date) TextView mDate;

    public VH(View root) {
      super(root);
      ButterKnife.bind(this, root);
    }
  }
}
