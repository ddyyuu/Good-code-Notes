package android.os;
import android.annotation.NonNull;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
/**
 * An adapter {@link Executor} that posts all executed tasks onto the given
 * {@link Handler}.
 *
 * @hide
 * android.os.HandlerExecutor是一个Android平台的Executor，它可以将任务发布到指定的Handler运行。
 * 可以将HandlerExecutor视为一种将任务提交到Handler的简单方法，与使用Handler.post（Runnable）相比，
 * HandlerExecutor具有更好的可读性和可维护性。
 *
 * 在Android中，UI线程（也称为主线程）用于处理UI相关的事件，例如更新UI，处理用户交互等等。如果您需要在UI线程上运行后台任务，
 * 可以使用HandlerExecutor。HandlerExecutor可以将后台任务发布到UI线程上的Handler上执行，这样可以避免在UI线程上执行长时间运行的操作，
 * 从而保持UI的响应性和流畅性。
 */
public class HandlerExecutor implements Executor {
    private final Handler mHandler;
    public HandlerExecutor(@NonNull Handler handler) {
        mHandler = Preconditions.checkNotNull(handler);
    }
    @Override
    public void execute(Runnable command) {
        if (!mHandler.post(command)) {
            throw new RejectedExecutionException(mHandler + " is shutting down");
        }
    }
}
///////////////////////////////////////////////////////////////////////////
//示例代码，使用HandlerExecutor将一个后台任务发布到UI线程上执行
// Handler uiHandler = new Handler(Looper.getMainLooper());
//HandlerExecutor uiExecutor = new HandlerExecutor(uiHandler);
//
//// 创建一个后台任务
//Runnable backgroundTask = new Runnable() {
//    @Override
//    public void run() {
//        // 执行后台任务
//    }
//};
//
//// 将后台任务发布到UI线程上执行
//uiExecutor.execute(backgroundTask);
///////////////////////////////////////////////////////////////////////////