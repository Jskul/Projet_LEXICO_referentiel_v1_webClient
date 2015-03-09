/**
 * Some utility methods and properties.
 */
;var lexico = {
		/**
		 * A set of warning messages.
		 */
		warnings: {
			cgramMissing: "Les catégories grammaticales sont inexistantes, essayez d'initialiser la base.",
			invalidOrthography: "Orthographe non valide.",
			invalidPhonology: "Phonologie non valide.",
			freqlivresInvalid: "Fréquence livres non valide.",
			freqfilmsInvalid: "Fréquence films non valide."
		},
		
		/**
		 * Checks that the given orthography is valid.
		 * 
		 * TODO too dirty, to be improved about latin characters
		 * TODO check and try to use xregexp.com/plugins/
		 * TODO exclude illegal sequences such as space, --, etc.
		 * 
		 * @param	ps_value	String	The string to be checked.
		 * 
		 * @returns	Boolean	Is <code>true</code> if the given value is a valid orthography, <code>false</code> otherwise.
		 */
		isValidOrthography: function (ps_value) {
			if (typeof ps_value !== "string" && !(ps_value instanceof String)) {
				throw new Error("parameter ps_value is not a string");
			}
			
			return /^[ a-zàâçéèêëîñôùû'-]+$/i.test(ps_value);
		},
		
		/**
		 * Checks that the given phonology is valid.
		 * 
		 * TODO too dirty, to be improved
		 * 
		 * @param	ps_value	String	The string to be checked.
		 * 
		 * @returns	Boolean	Is <code>true</code> if the given value is a valid phonology, <code>false</code> otherwise.
		 */
		isValidPhonology: function (ps_value) {
			if (typeof ps_value !== "string" && !(ps_value instanceof String)) {
				throw new Error("parameter ps_value is not a string");
			}
			
			return /^[aiyuoOeE°2951@§3j8wpbtdkgfvszSZmnNlRxG]+$/.test(ps_value);
		},
		
		/* Checks that the given value matches a two-decimal positive float.
		 * 
		 * @param	ps_value	String	The string to be checked as a valid float.
		 * 
		 * @returns	Boolean	Is <code>true</code> if the given value is a valid float, <code>false</code> otherwise.
		 */
		isValidFloat: function (ps_value) {
			if (typeof ps_value !== "string" && !(ps_value instanceof String)) {
				throw new Error("parameter ps_value is not a string");
			}
			
			return /^(\d{0,}(\.\d{0,2}){0,1}){0,1}$/.test(ps_value);
		},
		
		/**
		 * Gets URI query parameters.
		 * 
		 * @returns	{hash}	A hash of URI parameters/values.
		 */
		getQueryParams: function () {
			var _s_params = decodeURIComponent(window.location.search.substr(1));
			var _a_params = null;
			var _a_tmp = null;
			var _h_out = {};

			if (_s_params != null && _s_params != "") {
				_a_params = _s_params.split("&");
				
				for (var i=0; i<_a_params.length; ++i) {
					_a_tmp = _a_params[i].split("=");
					_h_out[_a_tmp[0]] = _a_tmp[1];
				}
			}

			return _h_out;
		}		
};