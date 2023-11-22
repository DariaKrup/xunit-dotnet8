using System;
using System.Threading;
using Xunit;
using Prime.Services;
using Xunit.Abstractions;

namespace Prime.UnitTests.Services
{
	public class PrimeService_Test1
	{
		private readonly ITestOutputHelper _testOutputHelper;
		private readonly PrimeService _primeService;

		public PrimeService_Test1(ITestOutputHelper testOutputHelper)
		{
			_testOutputHelper = testOutputHelper;
			_primeService = new PrimeService();
		}

		[Trait("Category", "Simple")]
		[Fact(DisplayName = "GET /api/books returns http status code 200")]
		public void ReturnFalseGivenValueOf1()
		{
			Console.WriteLine("some stdOut text");
			Console.Error.WriteLine("some stdErr text");
			
			var result = _primeService.IsPrime(1);

			Assert.False(result, $"1 should not be prime");
		}
		[Theory]
		[Trait("Category", "DataTests")]
		[InlineData(-1)]
		[InlineData(0)]
		[InlineData(1)]
		public void ReturnFalseGivenValuesLessThan2(int value)
		{
			Console.WriteLine("some stdOut text");
			Console.Error.WriteLine("some stdErr text");
			
			var result = _primeService.IsPrime(value);

			Assert.False(result, $"{value} should not be prime");
		}

		[Theory]
		[InlineData(-1)]
		[InlineData(0)]
		[InlineData(2)]
		public void ShouldFailTest(int value)
		{
			Console.WriteLine("some stdOut text");
			Console.Error.WriteLine("some stdErr text");
		
			var result = _primeService.IsPrime(value);

			Assert.False(result, $"{value} should not be prime");
		}
		
		[Theory]
		[Trait("Category", "RequiresDeployemnt")]
		[InlineData("987287498290909<script>alert(1)</script>", "987287498290909<script>")]
		[InlineData("<img src=987287498290909 onerror=alert(987287498290909) />", "<img src=987287498290909")]
		public void CheckXSSTest(string value1, string value2)
		{
			_testOutputHelper.WriteLine("some stdOut text");
			Console.Error.WriteLine("some stdErr text");
			Thread.Sleep(30000);
		
			_testOutputHelper.WriteLine("Value1 = " + value1);
			_testOutputHelper.WriteLine("Value2 = " + value2);
			

			Assert.False(true, "should not be prime");
		}

		[Fact(Skip = "Takes too long")]
		public void TestSomething()
		{
			Console.WriteLine("some stdOut text");
			Console.Error.WriteLine("some stdErr text");
			
			Assert.True(true);
		}
	}
}
