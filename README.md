
# react-native-rn-stripe-create-token

## Getting started

`$ npm install react-native-rn-stripe-create-token --save`

### Mostly automatic installation

`$ react-native link react-native-rn-stripe-create-token`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-rn-stripe-create-token` and add `RNRnStripeCreateToken.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNRnStripeCreateToken.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNRnStripeCreateTokenPackage;` to the imports at the top of the file
  - Add `new RNRnStripeCreateTokenPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-rn-stripe-create-token'
  	project(':react-native-rn-stripe-create-token').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-rn-stripe-create-token/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-rn-stripe-create-token')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNRnStripeCreateToken.sln` in `node_modules/react-native-rn-stripe-create-token/windows/RNRnStripeCreateToken.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Rn.Stripe.Create.Token.RNRnStripeCreateToken;` to the usings at the top of the file
  - Add `new RNRnStripeCreateTokenPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNRnStripeCreateToken from 'react-native-rn-stripe-create-token';

// TODO: What to do with the module?
RNRnStripeCreateToken;
```
  