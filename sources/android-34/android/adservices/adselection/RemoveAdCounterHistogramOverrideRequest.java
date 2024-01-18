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

package android.adservices.adselection;

import static android.adservices.adselection.SetAdCounterHistogramOverrideRequest.NULL_AD_COUNTER_KEY_MESSAGE;
import static android.adservices.adselection.SetAdCounterHistogramOverrideRequest.NULL_BUYER_MESSAGE;
import static android.adservices.adselection.UpdateAdCounterHistogramRequest.UNSET_AD_EVENT_TYPE_MESSAGE;
import static android.adservices.common.FrequencyCapFilters.AD_EVENT_TYPE_INVALID;

import android.adservices.common.AdTechIdentifier;
import android.adservices.common.FrequencyCapFilters;
import android.annotation.NonNull;
import android.annotation.Nullable;

import com.android.internal.util.Preconditions;

import java.util.Objects;

/**
 * Request object for removing ad counter histogram overrides.
 *
 * <p>Histogram overrides replace actual ad counter histograms used in ad selection. Overrides may
 * only be set in debuggable apps on phones running a debuggable OS build with developer options
 * enabled. Overrides are only available from the calling app.
 *
 * @hide
 */
// TODO(b/221876775): Unhide for frequency cap API review
public class RemoveAdCounterHistogramOverrideRequest {
    @FrequencyCapFilters.AdEventType private final int mAdEventType;
    @NonNull private final String mAdCounterKey;
    @NonNull private final AdTechIdentifier mBuyer;

    private RemoveAdCounterHistogramOverrideRequest(@NonNull Builder builder) {
        Objects.requireNonNull(builder);

        mAdEventType = builder.mAdEventType;
        mAdCounterKey = builder.mAdCounterKey;
        mBuyer = builder.mBuyer;
    }

    /**
     * Gets the {@link FrequencyCapFilters.AdEventType} for the ad counter histogram override.
     *
     * <p>The ad event type is used with the ad counter key from {@link #getAdCounterKey()} and the
     * buyer adtech from {@link #getBuyer()} to specify which histogram to use in ad selection
     * filtering. The ad event type would normally be specified by an app/SDK after a
     * FLEDGE-selected ad is rendered.
     */
    @FrequencyCapFilters.AdEventType
    public int getAdEventType() {
        return mAdEventType;
    }

    /**
     * Gets the ad counter key for the ad counter histogram override.
     *
     * <p>The ad counter key is used with the ad event type from {@link #getAdEventType()} and the
     * buyer adtech from {@link #getBuyer()} to specify which histogram to use in ad selection
     * filtering. The ad counter key would normally be specified by a custom audience ad to
     * represent a grouping to filter on.
     */
    @NonNull
    public String getAdCounterKey() {
        return mAdCounterKey;
    }

    /**
     * Gets the {@link AdTechIdentifier} for the buyer which owns the ad counter histogram.
     *
     * <p>During filtering in FLEDGE ad selection, ads can only use ad counter histogram data
     * generated by the same buyer. For {@link FrequencyCapFilters#AD_EVENT_TYPE_WIN}, ad counter
     * histogram data is further restricted to ads from the same custom audience, which is
     * identified by the buyer, the custom audience's owner app package name, and the custom
     * audience name.
     */
    @NonNull
    public AdTechIdentifier getBuyer() {
        return mBuyer;
    }

    @Override
    public String toString() {
        return "RemoveAdCounterHistogramOverrideRequest{"
                + "mAdEventType="
                + mAdEventType
                + ", mAdCounterKey='"
                + mAdCounterKey
                + "', mBuyer="
                + mBuyer
                + '}';
    }

    /** Builder for {@link RemoveAdCounterHistogramOverrideRequest} objects. */
    public static final class Builder {
        @FrequencyCapFilters.AdEventType private int mAdEventType = AD_EVENT_TYPE_INVALID;
        @Nullable private String mAdCounterKey;
        @Nullable private AdTechIdentifier mBuyer;

        public Builder() {}

        /**
         * Sets the {@link FrequencyCapFilters.AdEventType} for the ad counter histogram override.
         *
         * <p>See {@link #getAdEventType()} for more information.
         */
        @NonNull
        public Builder setAdEventType(@FrequencyCapFilters.AdEventType int adEventType) {
            mAdEventType = adEventType;
            return this;
        }

        /**
         * Sets the ad counter key for the ad counter histogram override.
         *
         * <p>See {@link #getAdCounterKey()} for more information.
         */
        @NonNull
        public Builder setAdCounterKey(@NonNull String adCounterKey) {
            Objects.requireNonNull(adCounterKey, NULL_AD_COUNTER_KEY_MESSAGE);
            mAdCounterKey = adCounterKey;
            return this;
        }

        /**
         * Sets the {@link AdTechIdentifier} for the buyer which owns the ad counter histogram.
         *
         * <p>See {@link #getBuyer()} for more information.
         */
        @NonNull
        public Builder setBuyer(@NonNull AdTechIdentifier buyer) {
            Objects.requireNonNull(buyer, NULL_BUYER_MESSAGE);
            mBuyer = buyer;
            return this;
        }

        /**
         * Builds the {@link RemoveAdCounterHistogramOverrideRequest} object.
         *
         * @throws NullPointerException if any parameters are not set
         * @throws IllegalArgumentException if the ad event type is invalid
         */
        @NonNull
        public RemoveAdCounterHistogramOverrideRequest build()
                throws NullPointerException, IllegalArgumentException {
            Preconditions.checkArgument(
                    mAdEventType != AD_EVENT_TYPE_INVALID, UNSET_AD_EVENT_TYPE_MESSAGE);
            Objects.requireNonNull(mAdCounterKey, NULL_AD_COUNTER_KEY_MESSAGE);
            Objects.requireNonNull(mBuyer, NULL_BUYER_MESSAGE);

            return new RemoveAdCounterHistogramOverrideRequest(this);
        }
    }
}