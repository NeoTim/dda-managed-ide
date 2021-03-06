; Licensed to the Apache Software Foundation (ASF) under one
; or more contributor license agreements. See the NOTICE file
; distributed with this work for additional information
; regarding copyright ownership. The ASF licenses this file
; to you under the Apache License, Version 2.0 (the
; "License"); you may not use this file except in compliance
; with the License. You may obtain a copy of the License at
;
; http://www.apache.org/licenses/LICENSE-2.0
;
; Unless required by applicable law or agreed to in writing, software
; distributed under the License is distributed on an "AS IS" BASIS,
; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
; See the License for the specific language governing permissions and
; limitations under the License.


(ns dda.pallet.dda-managed-ide.infra.clojure-test
  (:require
    [clojure.test :refer :all]
    [schema.core :as s]
    [dda.pallet.dda-managed-ide.infra.clojure :as sut]))

(s/set-fn-validation! true)

(deftest lein-user-profile-test
  (testing
    (is (re-find
         #"signing"
         (sut/lein-user-profile {:signing-gpg-key "gpg"})))
    (is (not (re-find
               #"signing"
               (sut/lein-user-profile {}))))
    (is (re-find
         #"clojars"
         (sut/lein-user-profile {:lein-auth [{:repo "clojars"
                                              :username "u"
                                              :password "p"}]})))
    (is (re-find
         #"otherrepo"
         (sut/lein-user-profile {:lein-auth [{:repo "clojars"
                                              :username "u"
                                              :password "p"}
                                             {:repo "otherrepo"
                                              :username "u"
                                              :password "p"}]})))
    (is (not (re-find
               #"username"
               (sut/lein-user-profile {:lein-auth []}))))))
