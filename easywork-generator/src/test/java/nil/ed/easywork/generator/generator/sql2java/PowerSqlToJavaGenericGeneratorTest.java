package nil.ed.easywork.generator.generator.sql2java;

import nil.ed.easywork.generator.config.Config;
import nil.ed.easywork.generator.context.PowerTemplateContext;
import nil.ed.easywork.generator.sql.SQLFileProcessor;
import nil.ed.easywork.generator.type.impl.AdsTypeMapper;
import nil.ed.easywork.generator.type.impl.MyBatisColTypeTransformer;
import nil.ed.easywork.template.FreeMarkerTemplateEngineAdapter;
import nil.ed.easywork.util.ClasspathFileUtils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class PowerSqlToJavaGenericGeneratorTest {

    private SQLFileProcessor processor = new SQLFileProcessor("/Users/admin/delin/sql/ad_material.sql",
            new AdsTypeMapper(), new MyBatisColTypeTransformer());

//    @Test
    public void generate() {
        Config config = new Config();
//        String basePkg = "com.kuaikan.ads.kyle.operation.log";
//        String basePkg = "com.kuaikan.ads.kyle.account.reserved";
//        String basePkg = "com.kuaikan.ads.kyle.ad";
        String basePkg = "com.kuaikan.ads.kyle.ad";
        String basePath = "/Users/admin/delin/generated/ad_material";
        config.setBasePkg(basePkg);
        config.setBasePath(basePath);
        config.setPrefix("");
        config.setProfile("kyle");
        PowerTemplateContext context = new PowerTemplateContext(ClasspathFileUtils.getClassPath("/new_tpl/" + config.getProfile()));

        PowerSqlToJavaGenericGenerator generator = new PowerSqlToJavaGenericGenerator(config, new FreeMarkerTemplateEngineAdapter(), processor);
        generator.generate(context);
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Path dir = Paths.get("/Users/admin/delin/private-workspace/EasyWork/easywork-generator/src/main/java/nil/ed/easywork/generator");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.java")) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        }
        BasicFileAttributeView view = Files.getFileAttributeView(dir, BasicFileAttributeView.class);
        BasicFileAttributes attributes = view.readAttributes();
        System.out.println(attributes.lastAccessTime().toString());
        System.out.println(attributes.lastModifiedTime().toString());
        System.out.println(attributes.fileKey().toString());
        System.out.println(attributes.creationTime().toString());
        System.out.println(view.getClass());

        FileOwnerAttributeView view2 = Files.getFileAttributeView(dir, FileOwnerAttributeView.class);
        UserPrincipal principal = view2.getOwner();
        System.out.println(principal.getName());
        System.out.println(principal);

        PosixFileAttributeView view3 = Files.getFileAttributeView(dir, PosixFileAttributeView.class);
        System.out.println(view3.readAttributes().permissions());

        futureDemo();
        callbackDemo();

    }

    public static void watcher(Path dir) throws IOException, InterruptedException {
        WatchService watcher = FileSystems.getDefault().newWatchService();
        WatchEvent.Kind<?>[] events = Arrays.asList(
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY
        ).toArray(new WatchEvent.Kind<?>[0]);
        WatchKey key = dir.register(watcher, events);
        Map<WatchKey, Path> map = new HashMap<>();
        map.putIfAbsent(key, dir);
        for (;;) {
            // wait for key to be signalled
            key = watcher.take();
            map.putIfAbsent(key, dir);
            System.out.println(map);
            // process events
            for (WatchEvent<?> ev: key.pollEvents()) {
                if (ev.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    if (ev.context() instanceof Path) {
                        // get path
                        Path evPath = (Path) ev.context();
                        // do something
                    }
                    // on create
                } else if (ev.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                    // on delete
                } else if (ev.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                    // on modify
                }

            }// reset key
            key.reset();
        }
    }

    public static void futureDemo() throws InterruptedException, ExecutionException, IOException {
        new Thread(()-> {
            try (ServerSocketChannel serverSocket = ServerSocketChannel.open()){
                serverSocket.bind(new InetSocketAddress("127.0.0.1", 9455));
                System.out.println("accept...");
                SocketChannel c = serverSocket.accept();
                System.out.println("accepted");
                c.write(ByteBuffer.wrap("thanks".getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(2);

        try (AsynchronousSocketChannel ch = AsynchronousSocketChannel.open()) {
            Future<Void> connectFuture = ch.connect(new InetSocketAddress("127.0.0.1", 9455));
            connectFuture.get();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            Future<Integer> f = ch.read(buf);
            f.get();
            System.out.println(new String(buf.array()));
        }
    }

    public static void callbackDemo() throws InterruptedException, IOException {
        new Thread(()-> {
            try (ServerSocketChannel serverSocket = ServerSocketChannel.open()){
                serverSocket.bind(new InetSocketAddress("127.0.0.1", 9456));
                System.out.println("accept...");
                SocketChannel c = serverSocket.accept();
                System.out.println("accepted");
                c.write(ByteBuffer.wrap("thanks".getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(2);
        /*
        Callback Style
         */
        AsynchronousSocketChannel ch2 = AsynchronousSocketChannel.open();
        ch2.connect(new InetSocketAddress("127.0.0.1", 9456), ch2, new CompletionHandler<Void, AsynchronousSocketChannel>() {
            @Override
            public void completed(Void result, AsynchronousSocketChannel attachment) {
                try {
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    attachment.read(buf, attachment, new CompletionHandler<Integer, AsynchronousSocketChannel>() {
                        @Override
                        public void completed(Integer result, AsynchronousSocketChannel attachment) {
                            try {
                                System.out.println(new String(buf.array()));
                                System.out.println(result);
                                attachment.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
                            exc.printStackTrace();
                            System.out.println("failed");
                            try {
                                attachment.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
                try {
                    exc.printStackTrace();
                    attachment.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        TimeUnit.SECONDS.sleep(3);
    }

}