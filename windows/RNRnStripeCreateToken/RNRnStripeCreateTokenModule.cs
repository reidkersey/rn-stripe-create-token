using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Rn.Stripe.Create.Token.RNRnStripeCreateToken
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNRnStripeCreateTokenModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNRnStripeCreateTokenModule"/>.
        /// </summary>
        internal RNRnStripeCreateTokenModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNRnStripeCreateToken";
            }
        }
    }
}
