//
//  MicrophonePowerObserver.swift
//  iosApp
//
//  Created by Anjali Prajapati on 17/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import Combine
import Speech

class MicrophonePowerObserver : ObservableObject {
    
    private var cancellable : AnyCancellable? = nil
    private var audioRecorder : AVAudioRecorder? = nil
    
    @Published private(set) var micPowerRatio = 0.0
    
    private let powerEmissionsPerSecond = 20.0
    
    func startObserving(){
        do{
            let recordingSettings:[String:Any] = [
                AVFormatIDKey : NSNumber(value:kAudioFormatAppleLossless),
                AVNumberOfChannelsKey :1
            ]
            
            let recorder = try AVAudioRecorder(url: URL(fileURLWithPath: "/dev/null",isDirectory: true), settings: recordingSettings)
            recorder.isMeteringEnabled = true
            recorder.record()
            self.audioRecorder = recorder
            self.cancellable = Timer.publish(
                every: 1.0 / powerEmissionsPerSecond,
                tolerance: 1.0 / powerEmissionsPerSecond,
                on: .main,
                in: .common
            )
            .autoconnect()
            .sink(receiveValue: {[weak self] _ in
                recorder.updateMeters()
                let powerOffset = recorder.averagePower(forChannel: 0)
                if powerOffset < -50 {
                    self?.micPowerRatio = 0
                }else{
                    let normalizedOffset = CGFloat(50 + powerOffset) / 50
                    self?.micPowerRatio = normalizedOffset
                }
            })
        }catch{
            print("An Error Occured When Oberving MicroPhone Power \(error.localizedDescription)")
        }
    }
    
    
    func release(){
        cancellable = nil
        audioRecorder?.stop()
        self.audioRecorder = nil
    }
}
