/*
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
define([ '{lodash}/lodash', '{angular}/angular' ], function (_, angular) {
    var seedBusiness = angular.module('seedBusiness', [ ]);

    seedBusiness.config(['$httpProvider', function ($httpProvider) {
        $httpProvider.defaults.transformResponse.push(function (data) {
            if (typeof data.resultSize !== 'undefined' && typeof data.view !== 'undefined' && data.view instanceof Array) {
                var prototype = Object.getPrototypeOf(data.view);

                prototype.$viewInfo = _.extend({}, data);
                delete prototype.$viewInfo.view;

                return data.view;
            }

            return data;
        });
    }]);

    return {
        angularModules: [ 'seedBusiness' ]
    };
});
