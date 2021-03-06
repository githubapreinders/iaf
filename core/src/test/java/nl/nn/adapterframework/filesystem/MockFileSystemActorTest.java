package nl.nn.adapterframework.filesystem;

import java.util.Date;

public class MockFileSystemActorTest extends FileSystemActorExtraTest <MockFile,MockFileSystem<MockFile>>{

	
	@Override
	protected IFileSystemTestHelperFullControl getFileSystemTestHelper() {
		return new MockFileSystemTestHelper<MockFile>();
	}

	@Override
	protected MockFileSystem<MockFile> createFileSystem() {
		return ((MockFileSystemTestHelper<MockFile>)helper).getFileSystem();
	}

}
