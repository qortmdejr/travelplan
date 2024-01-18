/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.health.connect;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.health.connect.datatypes.Record;

import java.util.Objects;

/** A class to represent filtering based on record id */
public class RecordIdFilter {
    private final Class<? extends Record> mRecordType;
    private final String mId;
    private final String mClientRecordId;

    /**
     * Creates an instance of Record id filter based on client record id.
     *
     * @param recordType Record class for which the client record id must be set.
     * @param clientRecordId Client identifier that was set while inserting the record.
     * @return Object of {@link RecordIdFilter}
     */
    @NonNull
    public static RecordIdFilter fromClientRecordId(
            @NonNull Class<? extends Record> recordType, @NonNull String clientRecordId) {
        Objects.requireNonNull(recordType);
        Objects.requireNonNull(clientRecordId);
        return new RecordIdFilter(recordType, null, clientRecordId);
    }

    /**
     * Creates an instance of Record id filter based on record id.
     *
     * @param recordType Record class for which the id must be set.
     * @param id Identifier generated by the platform and returned by {@link
     *     HealthConnectManager#insertRecords}
     * @return Object of {@link RecordIdFilter}
     */
    @NonNull
    public static RecordIdFilter fromId(
            @NonNull Class<? extends Record> recordType, @NonNull String id) {
        Objects.requireNonNull(recordType);
        Objects.requireNonNull(id);
        return new RecordIdFilter(recordType, id, null);
    }

    private RecordIdFilter(Class<? extends Record> recordType, String id, String clientRecordId) {
        mRecordType = recordType;
        mId = id;
        mClientRecordId = clientRecordId;
    }

    /**
     * @return Record class for this identifier
     */
    @NonNull
    public Class<? extends Record> getRecordType() {
        return mRecordType;
    }

    /**
     * @return Identifier given by the platform
     */
    @Nullable
    public String getId() {
        return mId;
    }

    /**
     * @return Client record identifier
     */
    @Nullable
    public String getClientRecordId() {
        return mClientRecordId;
    }
}
