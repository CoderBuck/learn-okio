
import okio.Buffer;
import okio.ForwardingSource;
import okio.Source;

import java.io.IOException;
import java.text.SimpleDateFormat;


/**
 * 监听下载/上传进度
 */
public class MarkSource extends ForwardingSource {


    private long totalBytesRead = 0L;
    private long totalBytesCount = 0L;
    private int percent = 0;

    public MarkSource(Source delegate) {
        super(delegate);
    }

    public MarkSource(Source delegate, long totalBytesCount) {
        super(delegate);
        this.totalBytesCount = totalBytesCount;
    }

    @Override
    public long read(Buffer sink, long byteCount) throws IOException {
        long bytesRead = super.read(sink, byteCount);
        totalBytesRead += bytesRead != -1 ? bytesRead : 0;

        int l = (int) (totalBytesRead * 100 / totalBytesCount);
        if (l > percent) {
            percent = l;
            long millis = System.currentTimeMillis();
            SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
            System.out.println(percent + " %  " + formatter.format(millis));
        }
        return bytesRead;
    }
}
